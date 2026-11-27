package com.backend.hotelbooking.dto.response;

import com.backend.hotelbooking.entity.BookingStatus;
import com.backend.hotelbooking.entity.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResult(
        Long id,
        Long hotelId, String hotelName, String hotelCity,
        Long roonId, Integer roomNumber, RoomType roomType,
        Long guestId, String guestName, String guestEmail,
        LocalDate checkInDate, LocalDate checkOutDate, long nights,
        Integer numberOfGuests,
        BigDecimal pricePerNight, BigDecimal totalAmount,
        BookingStatus status,
        LocalDateTime createdAt
) {
}
