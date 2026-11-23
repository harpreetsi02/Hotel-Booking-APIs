package com.backend.hotelbooking.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class HotelResponse {
    Long id;
    String name;
    String city;
    String address;
    Integer starRating;
    LocalDateTime createdAt;
}