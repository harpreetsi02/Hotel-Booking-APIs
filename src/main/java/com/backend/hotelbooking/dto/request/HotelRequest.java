package com.backend.hotelbooking.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class HotelRequest {

    @NotBlank(message = "Hotel name is required")
    @Size(max = 100, message = "Hotel name must be at most 100 characters")
    private String name;

    @NotBlank(message = "City is required")
    @Size(max = 60, message = "City must be at most 60 characters")
    private String city;

    @NotBlank(message = "Address is required")
    @Size(max = 255, message = "Address must be at most 255 characters")
    private String address;

    @NotNull(message = "Star rating is required")
    @Min(value = 1, message = "Star rating must be at least 1")
    @Max(value = 5, message = "Star rating must be at most 5")
    private Integer starRating;
}