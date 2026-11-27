package com.backend.hotelbooking.mapper;

import com.backend.hotelbooking.dto.response.BookingCommand;
import com.backend.hotelbooking.dto.response.BookingResult;
import com.backend.hotelbooking.dto.v2.BookingRequestV2;
import com.backend.hotelbooking.dto.v2.BookingResponseV2;
import org.springframework.stereotype.Component;

@Component
public class BookingV2Mapper {

    public BookingCommand toCommand(BookingRequestV2 r) {
        return new BookingCommand(r.roomId(), r.guestId(),
                r.stay().checkIn(), r.stay().checkOut(), r.numberOfGuests());
    }

    public BookingResponseV2 toResponse(BookingResult r) {
        return new BookingResponseV2(
                r.id(),
                r.status(),
                new BookingResponseV2.HotelInfo(r.hotelId(), r.hotelName(), r.hotelCity()),
                new BookingResponseV2.RoomInfo(r.roonId(), r.roomNumber(), r.roomType()),
                new BookingResponseV2.GuestInfo(r.guestId(), r.guestName(), r.guestEmail()),
                new BookingResponseV2.StayInfo(r.checkInDate(), r.checkOutDate(), r.nights(), r.numberOfGuests()),
                new BookingResponseV2.PriceInfo(r.pricePerNight(), r.totalAmount(), "INR"),
                r.createdAt());
    }
}
