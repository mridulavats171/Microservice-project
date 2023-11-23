package com.micro.hotel.hotelservice.Service;

import com.micro.hotel.hotelservice.Domain.Hotel;
import com.micro.hotel.hotelservice.Exception.ResourceNotFoundException;
import com.micro.hotel.hotelservice.Repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HotelService {
    @Autowired
    private HotelRepository hotelRepository;


    public Hotel create(Hotel hotel) {
        String hotelId = UUID.randomUUID().toString();
        hotel.setId(hotelId);
        return hotelRepository.save(hotel);
    }


    public List<Hotel> getAll() {
        return hotelRepository.findAll();
    }


    public Hotel get(String id) {
        return hotelRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("hotel with given id not found !!"));
    }
}
