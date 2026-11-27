package com.backend.hotelbooking.mapper;

import com.backend.hotelbooking.dto.response.BookingCommand;
import com.backend.hotelbooking.dto.response.BookingResult;
import com.backend.hotelbooking.dto.v1.BookingRequestV1;
import com.backend.hotelbooking.dto.v1.BookingResponseV1;
import org.springframework.stereotype.Component;

@Component
public class BookingV1Mapper {

    public BookingCommand toCommand(BookingRequestV1 r) {
        return new BookingCommand(
                r.getRoomId(),
                r.getGuestId(),
                r.getCheckIn(),
                r.getCheckOut(),
                1
        );
    }

    public BookingResponseV1 toResponse(BookingResult r) {
        return BookingResponseV1.builder()
                .id(r.id())
                .hotelName(r.hotelName())
                .roomNumber(r.roomNumber())
                .guestName(r.guestName())
                .checkIn(r.checkInDate())
                .checkOut(r.checkOutDate())
                .totalAmount(r.totalAmount())
                .status(r.status())
                .build();
    }
}
