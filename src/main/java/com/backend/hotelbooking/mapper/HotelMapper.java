package com.backend.hotelbooking.mapper;

import com.backend.hotelbooking.dto.request.HotelRequest;
import com.backend.hotelbooking.dto.response.HotelResponse;
import com.backend.hotelbooking.entity.Hotel;
import org.springframework.stereotype.Component;

@Component
public class HotelMapper {

    public Hotel toEntity(HotelRequest request) {
        return Hotel.builder()
                .name(request.getName())
                .city(request.getCity())
                .address(request.getAddress())
                .starRating(request.getStarRating())
                .build();
    }

    public HotelResponse toResponse(Hotel hotel) {
        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .city(hotel.getCity())
                .address(hotel.getAddress())
                .starRating(hotel.getStarRating())
                .createdAt(hotel.getCreatedAt())
                .build();
    }
}