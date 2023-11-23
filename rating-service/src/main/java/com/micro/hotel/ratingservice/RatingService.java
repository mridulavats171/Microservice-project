package com.micro.hotel.ratingservice;

import com.micro.hotel.ratingservice.Domain.Rating;
import com.micro.hotel.ratingservice.Domain.Repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;


    public Rating create(Rating rating) {
        return ratingRepository.save(rating);
    }


    public List<Rating> getRatings() {
        return ratingRepository.findAll();
    }


    public List<Rating> getRatingByUserId(String userId) {
        try{
          List<Rating> ratings =  ratingRepository.findByUserId(userId);
        }catch(WebClientResponseException ex) {
            System.err.println("Response Body: " + ex.getResponseBodyAsString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return ratingRepository.findByUserId(userId);
    }


    public List<Rating> getRatingByHotelId(String hotelId) {
        return ratingRepository.findByHotelId(hotelId);
    }
}
