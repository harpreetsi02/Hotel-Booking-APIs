package com.backend.hotelbooking.dto.request;

import com.backend.hotelbooking.entity.RoomType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record RoomRequest(

        @NotNull(message = "Room number is required")
        @Min(value = 1, message = "Room number must be at least 1")
        Integer roomNumber,

        @NotNull(message = "Type is required")
        RoomType type,

        @NotNull(message = "Price per night is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
        @Digits(integer = 8, fraction = 2, message = "Price format is invalid")
        BigDecimal pricePerNight,

        @NotNull(message = "Capacity is required")
        @Min(value = 1, message = "Capacity must be at least 1")
        Integer capacity
) {
}
