package com.micro.userservice.Controller;

import com.micro.userservice.Client.HotelClient;
import com.micro.userservice.Client.RatingClient;
import com.micro.userservice.Domain.Hotel;
import com.micro.userservice.Domain.Rating;
import com.micro.userservice.Domain.Users;
import com.micro.userservice.Service.UserService;
import com.micro.userservice.externalServices.FiegnHotel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RatingClient ratingClient;

    @Autowired
    private FiegnHotel fiegnHotel;

    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users users){
        Users users1 = userService.saveUser(users);
        return ResponseEntity.status(HttpStatus.CREATED).body(users1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable String id){
        Users users = userService.getUser(id);
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    @GetMapping
    public ResponseEntity<List<Users>> getAllUsers(){
        List<Users> userList = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.FOUND).body(userList);
    }

    @GetMapping("withrating/{id}")
    @CircuitBreaker(name = "ratingBreaker", fallbackMethod = "ratingFallback")
    public ResponseEntity<Users> getUserByIdWithRating(@PathVariable String id){
        Users users = userService.getUser(id);
        List<Rating> ratingList = ratingClient.getRatingsByUserId(id);
        List<Rating> list = ratingList.stream().map(rating -> {
            Hotel hotel = fiegnHotel.getHotel(rating.getHotelId());
            rating.setHotel(hotel);
            return rating;
        }).collect(Collectors.toList());
        users.setRatings(list);
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    @GetMapping("/withrating/all")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratinguserFallback")
    public ResponseEntity<List<Users>> getAllUsersWithRatings(){
        List<Users> userList = userService.getAllUsers();
        userList.forEach(users -> users.setRatings(ratingClient.getRatingsByUserId(users.getUserId())));
        return ResponseEntity.status(HttpStatus.FOUND).body(userList);
    }

    public ResponseEntity<Users>  ratingFallback(String id, Exception ex){
        logger.info("fallback is initiated because service is down: " + ex.getMessage());
        Users  user = Users.builder().email("dummy@gmail.com")
                .about("this is dummy user because some service is down")
                .name("dummy")
                .userId("34324")
                .build();
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    public ResponseEntity<List<Users>> ratinguserFallback(Exception ex){
        logger.info("fallback is initiated because service is down: " + ex.getMessage());
        Users  user = Users.builder().email("dummy@gmail.com")
                .about("this is dummy user because some service is down")
                .name("dummy")
                .userId("34324")
                .build();
        List<Users> usersList = new ArrayList<>();
        usersList.add(user);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

}
