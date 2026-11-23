package com.backend.hotelbooking.dto.response;

import java.time.LocalDateTime;

public record GuestResponse(
        Long id,
        String fullName,
        String email,
        String phone,
        LocalDateTime createdAt
) {
}
