package com.micro.userservice.Client;

import com.micro.userservice.Domain.Hotel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

@HttpExchange
public interface HotelClient {

    @GetExchange("hotels/{hotelId}")
    public ResponseEntity<Hotel> createHotel(@PathVariable String hotelId);
}
