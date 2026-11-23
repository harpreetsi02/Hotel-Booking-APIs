package com.backend.hotelbooking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record GuestRequest(

        @NotBlank(message = "Full name is required")
        @Size(max = 100, message = "Full name must be at most 100 characters")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Enter valid email")
        String email,

        @NotBlank(message = "Phone number is required")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "Phone must be exactly 10 digits"
        )
        String phone
) {
}
