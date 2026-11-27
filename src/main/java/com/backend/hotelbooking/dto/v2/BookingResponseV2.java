package com.backend.hotelbooking.dto.v2;

import com.backend.hotelbooking.entity.BookingStatus;
import com.backend.hotelbooking.entity.RoomType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookingResponseV2(
        Long id,
        BookingStatus status,
        HotelInfo hotel,
        RoomInfo room,
        GuestInfo guest,
        StayInfo stay,
        PriceInfo price,
        LocalDateTime createdAt
) {
    public record HotelInfo(Long id, String name, String city) {}
    public record RoomInfo(Long id, Integer number, RoomType type) {}
    public record GuestInfo(Long id, String fullName, String email) {}
    public record StayInfo(LocalDate checkIn, LocalDate checkOut, long nights, Integer numberOfGuests) {}
    public record PriceInfo(BigDecimal perNight, BigDecimal total, String currency) {}
}
