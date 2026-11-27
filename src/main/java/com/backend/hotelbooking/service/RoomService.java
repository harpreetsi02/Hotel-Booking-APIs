package com.backend.hotelbooking.service;

import com.backend.hotelbooking.dto.request.RoomRequest;
import com.backend.hotelbooking.dto.response.RoomResponse;
import com.backend.hotelbooking.entity.Hotel;
import com.backend.hotelbooking.entity.Room;
import com.backend.hotelbooking.exception.DuplicateResourceException;
import com.backend.hotelbooking.exception.ResourceInUseException;
import com.backend.hotelbooking.exception.ResourceNotFoundException;
import com.backend.hotelbooking.mapper.RoomMapper;
import com.backend.hotelbooking.repository.BookingRepository;
import com.backend.hotelbooking.repository.HotelRepository;
import com.backend.hotelbooking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;
    private final RoomMapper roomMapper;
    private final BookingRepository bookingRepository;

    @Transactional
    public RoomResponse create(Long hotelId, RoomRequest request){
        Hotel hotel = findHotel(hotelId);
        if (roomRepository.existsByHotel_IdAndRoomNumber(hotelId, request.roomNumber())){
            throw new DuplicateResourceException("Room",
                    "number" + request.roomNumber() + "in hotel " + hotelId);
        }

        Room room = roomMapper.toEntity(request, hotel);
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toResponse(savedRoom);
    }

    @Transactional(readOnly = true)
    public Page<RoomResponse> findAll(Long hotelId, Pageable pageable){
        if (!hotelRepository.existsById(hotelId)){
            throw new ResourceNotFoundException("Hotel", hotelId);
        }

        return roomRepository.findAllByHotelIdWithHotel(hotelId, pageable)
                .map(roomMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public RoomResponse findById(Long roomId, Long hotelId){
        return roomMapper.toResponse(findRoomOrThrow(roomId, hotelId));
    }

    @Transactional
    public RoomResponse update(Long roomId, Long hotelId, RoomRequest request){
        Room room = findRoomOrThrow(roomId, hotelId);
        if (roomRepository.existsByHotel_IdAndRoomNumberAndIdNot(hotelId, request.roomNumber(), roomId)) {
            throw new DuplicateResourceException("Room",
                    "number " + request.roomNumber() + " in hotel " + hotelId);
        }
        room.setRoomNumber(request.roomNumber());
        room.setType(request.type());
        room.setPricePerNight(request.pricePerNight());
        room.setCapacity(request.capacity());
        return roomMapper.toResponse(room);
    }

    @Transactional
    public void delete(Long roomId, Long hotelId){
        if (bookingRepository.existsByRoom_Id(roomId)) {
            throw new ResourceInUseException("Room " + roomId + " cannot be deleted: it has bookings");
        }
        roomRepository.delete(findRoomOrThrow(roomId, hotelId));
    }

    private Room findRoomOrThrow(Long roomId, Long hotelId){
        return roomRepository.findByIdAndHotelIdWithHotel(roomId, hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Room", roomId));
    }

    private Hotel findHotel(Long hotelId){
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", hotelId));
    }
}
