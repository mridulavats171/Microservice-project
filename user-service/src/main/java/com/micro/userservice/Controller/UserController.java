package com.micro.userservice.Controller;

import com.micro.userservice.Client.RatingClient;
import com.micro.userservice.Domain.Rating;
import com.micro.userservice.Domain.Users;
import com.micro.userservice.Service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        users.setRatings(ratingClient.getRatingsByUserId(id));
        return ResponseEntity.status(HttpStatus.FOUND).body(users);
    }

    @GetMapping("/withrating/all")
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

}
