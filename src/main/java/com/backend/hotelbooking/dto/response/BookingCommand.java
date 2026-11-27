package com.backend.hotelbooking.dto.response;

import java.time.LocalDate;

public record BookingCommand(
        Long roomId,
        Long guestId,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer numberOfGuests
) {
}
