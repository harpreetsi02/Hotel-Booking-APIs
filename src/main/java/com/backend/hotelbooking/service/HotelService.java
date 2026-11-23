package com.backend.hotelbooking.service;

import com.backend.hotelbooking.dto.request.HotelRequest;
import com.backend.hotelbooking.dto.response.HotelResponse;
import com.backend.hotelbooking.entity.Hotel;
import com.backend.hotelbooking.exception.DuplicateResourceException;
import com.backend.hotelbooking.exception.ResourceInUseException;
import com.backend.hotelbooking.exception.ResourceNotFoundException;
import com.backend.hotelbooking.mapper.HotelMapper;
import com.backend.hotelbooking.repository.HotelRepository;
import com.backend.hotelbooking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final HotelMapper hotelMapper;

    @Transactional(readOnly = true)
    public Page<HotelResponse> getAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable).map(hotelMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public HotelResponse getHotelById(Long id) {
        return hotelMapper.toResponse(findHotelOrThrow(id));
    }

    @Transactional
    public HotelResponse createHotel(HotelRequest request) {
        if (hotelRepository.existsByNameIgnoreCaseAndCityIgnoreCase(request.getName(), request.getCity())) {
            throw new DuplicateResourceException("Hotel", request.getName() + " (" + request.getCity() + ")");
        }
        Hotel saved = hotelRepository.save(hotelMapper.toEntity(request));
        return hotelMapper.toResponse(saved);
    }

    @Transactional
    public HotelResponse updateHotel(Long id, HotelRequest request) {
        Hotel hotel = findHotelOrThrow(id);
        if (hotelRepository.existsByNameIgnoreCaseAndCityIgnoreCaseAndIdNot(
                request.getName(), request.getCity(), id)) {
            throw new DuplicateResourceException("Hotel", request.getName() + " (" + request.getCity() + ")");
        }
        hotel.setName(request.getName());
        hotel.setCity(request.getCity());
        hotel.setAddress(request.getAddress());
        hotel.setStarRating(request.getStarRating());
        return hotelMapper.toResponse(hotel); // dirty checking save karega
    }

    @Transactional
    public void deleteHotel(Long id) {
        Hotel hotel = findHotelOrThrow(id);
        if (roomRepository.existsByHotel_Id(id)) {
            throw new ResourceInUseException(
                    "Hotel " + id + " cannot be deleted: it still has rooms");
        }
        hotelRepository.delete(hotel);
    }

    private Hotel findHotelOrThrow(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel", id));
    }
}