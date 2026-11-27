package com.backend.hotelbooking.controller;

import com.backend.hotelbooking.dto.response.BookingResult;
import com.backend.hotelbooking.dto.v2.BookingRequestV2;
import com.backend.hotelbooking.dto.v2.BookingResponseV2;
import com.backend.hotelbooking.entity.BookingStatus;
import com.backend.hotelbooking.mapper.BookingV2Mapper;
import com.backend.hotelbooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings v2", description = "Booking APIs, API version 2")
@RequiredArgsConstructor
public class BookingControllerV2 {

    private final BookingService bookingService;
    private final BookingV2Mapper mapper;

    @Operation(summary = "Create a booking (v2)")
    @PostMapping(version = "2")
    public ResponseEntity<BookingResponseV2> create(@Valid @RequestBody BookingRequestV2 request) {
        BookingResult result = bookingService.create(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }

    @Operation(summary = "Get a booking (v2)")
    @GetMapping(path = "/{id}", version = "2")
    public BookingResponseV2 findById(@PathVariable Long id) {
        return mapper.toResponse(bookingService.findById(id));
    }

    @Operation(summary = "List bookings (v2)")
    @GetMapping(version = "2")
    public Page<BookingResponseV2> findAll(
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) BookingStatus status,
            @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return bookingService.findAll(guestId, status, pageable).map(mapper::toResponse);
    }
}
