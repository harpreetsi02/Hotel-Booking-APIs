package com.backend.hotelbooking.dto.v2;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record BookingRequestV2(
        @NotNull Long roomId,
        @NotNull Long guestId,
        @NotNull @Valid StayRequest stay,
        @NotNull @Min(1) Integer numberOfGuests   // v2 mein naya REQUIRED field
) {
    public record StayRequest(@NotNull LocalDate checkIn, @NotNull LocalDate checkOut) {}
}