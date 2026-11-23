package com.backend.hotelbooking.mapper;

import com.backend.hotelbooking.dto.request.GuestRequest;
import com.backend.hotelbooking.dto.response.GuestResponse;
import com.backend.hotelbooking.entity.Guest;
import org.springframework.stereotype.Component;

@Component
public class GuestMapper {

    public Guest toEntity(GuestRequest request){

        return Guest.builder()
                .fullName(request.fullName())
                .email(request.email())
                .phone(request.phone())
                .build();
    }

    public GuestResponse toResponse(Guest guest){

        return new GuestResponse(
                guest.getId(),
                guest.getFullName(),
                guest.getEmail(),
                guest.getPhone(),
                guest.getCreatedAt()
        );
    }
}
