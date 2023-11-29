package com.micro.hotel.hotelservice;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class CityConstraintValidator implements ConstraintValidator<City, String> {

    @Override
    public boolean isValid(String city, ConstraintValidatorContext constraintValidatorContext) {
        List list = Arrays.asList(new String[]{"DELHI", "MUMBAI", "BANGALORE"});
        return list.contains(city);
    }
}
