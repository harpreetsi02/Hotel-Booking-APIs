package com.backend.hotelbooking.dto.v1;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BookingRequestV1 {
    @NotNull
    private Long roomId;
    @NotNull private Long guestId;
    @NotNull private LocalDate checkIn;
    @NotNull private LocalDate checkOut;
}