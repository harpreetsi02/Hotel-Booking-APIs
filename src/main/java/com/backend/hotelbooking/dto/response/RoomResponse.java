package com.backend.hotelbooking.dto.response;

import com.backend.hotelbooking.entity.RoomType;

import java.math.BigDecimal;

public record RoomResponse(
        Long id,
        Long hotelId,
        String hotelName,
        Integer roomNumber,
        RoomType type,
        BigDecimal pricePerNight,
        Integer capacity,
        Boolean available
) {
}
