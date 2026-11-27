package com.backend.hotelbooking.dto.v1;

import com.backend.hotelbooking.entity.BookingStatus;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class BookingResponseV1 {
    Long id;
    String hotelName;
    Integer roomNumber;
    String guestName;
    LocalDate checkIn;
    LocalDate checkOut;
    BigDecimal totalAmount;
    BookingStatus status;
}
