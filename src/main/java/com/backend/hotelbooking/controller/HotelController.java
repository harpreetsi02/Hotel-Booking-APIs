package com.backend.hotelbooking.controller;

import com.backend.hotelbooking.dto.request.HotelRequest;
import com.backend.hotelbooking.dto.response.HotelResponse;
import com.backend.hotelbooking.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels", description = "Hotel management APIs")
public class HotelController {

    private final HotelService hotelService;

    @GetMapping
    @Operation(summary = "List hotels (paginated)")
    public ResponseEntity<Page<HotelResponse>> getAllHotels(
            @ParameterObject @PageableDefault(size = 10, sort = "name") Pageable pageable
    ) {
        Page<HotelResponse> response =
                hotelService.getAllHotels(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get hotel by id")
    public ResponseEntity<HotelResponse> getHotelById(
            @PathVariable Long id
    ) {
        HotelResponse response =
                hotelService.getHotelById(id);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @Operation(summary = "Create a hotel")
    public ResponseEntity<HotelResponse> createHotel(
            @Valid @RequestBody HotelRequest request
    ) {
        HotelResponse response =
                hotelService.createHotel(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a hotel")
    public ResponseEntity<HotelResponse> updateHotel(
            @PathVariable Long id,
            @Valid @RequestBody HotelRequest request
    ) {
        HotelResponse response =
                hotelService.updateHotel(id, request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a hotel")
    public ResponseEntity<Void> deleteHotel(
            @PathVariable Long id
    ) {
        hotelService.deleteHotel(id);
        return ResponseEntity.noContent().build();
    }
}