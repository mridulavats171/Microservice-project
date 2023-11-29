package com.micro.userservice.Client;

import com.micro.userservice.Domain.Rating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.List;


    @HttpExchange
    public interface RatingClient {
        @GetExchange("/ratings/users/{userId}")
        public List<Rating> getRatingsByUserId(@PathVariable String userId);
        //gyhinijnm

    }


