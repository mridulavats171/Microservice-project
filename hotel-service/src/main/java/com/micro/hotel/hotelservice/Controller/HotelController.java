package com.micro.hotel.hotelservice.Controller;

import com.micro.hotel.hotelservice.Domain.Hotel;
import com.micro.hotel.hotelservice.Service.HotelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
    @RequestMapping("/hotels")
    public class HotelController {

        @Autowired
        private HotelService hotelService;

        //create


        @PostMapping
        public ResponseEntity<Hotel> createHotel(@Valid @RequestBody Hotel hotel) {
            return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.create(hotel));
        }


        //get single

        @GetMapping("/{hotelId}")
        public ResponseEntity<Hotel> createHotel(@PathVariable String hotelId) {
            return ResponseEntity.status(HttpStatus.OK).body(hotelService.get(hotelId));
        }


        //get all
        @GetMapping
        public ResponseEntity<List<Hotel>> getAll(){
            return ResponseEntity.ok(hotelService.getAll());
        }

    }
