package com.backend.hotelbooking.mapper;

import com.backend.hotelbooking.dto.request.RoomRequest;
import com.backend.hotelbooking.dto.response.RoomResponse;
import com.backend.hotelbooking.entity.Hotel;
import com.backend.hotelbooking.entity.Room;
import org.springframework.stereotype.Component;

@Component
public class RoomMapper {

    public Room toEntity(RoomRequest request, Hotel hotel){

        return Room.builder()
                .hotel(hotel)
                .roomNumber(request.roomNumber())
                .type(request.type())
                .pricePerNight(request.pricePerNight())
                .capacity(request.capacity())
                .build();
    }

    public RoomResponse toResponse(Room room){

        return new RoomResponse(
                room.getId(),
                room.getHotel().getId(),
                room.getHotel().getName(),
                room.getRoomNumber(),
                room.getType(),
                room.getPricePerNight(),
                room.getCapacity(),
                room.isAvailable()
        );
    }
}
