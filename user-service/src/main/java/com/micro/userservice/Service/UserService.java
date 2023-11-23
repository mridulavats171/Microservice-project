package com.micro.userservice.Service;

import com.micro.userservice.Domain.Users;
import com.micro.userservice.Exception.ResourceNotFoundException;
import com.micro.userservice.Repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UsersRepo usersRepo;


    public Users getUser(String id){
        return usersRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException());
    }

    @PostMapping
    public Users saveUser(Users users){
        String randomUserId = UUID.randomUUID().toString();
        users.setUserId(randomUserId);
        return usersRepo.save(users);
    }

    public List<Users> getAllUsers(){
        return usersRepo.findAll();
    }

}
