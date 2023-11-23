package com.micro.hotel.ratingservice.Domain.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.micro.hotel.ratingservice.Domain.Rating;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String>{
    List<Rating> findByUserId(String userId);
    List<Rating> findByHotelId(String hotelId);
}
