package com.backend.hotelbooking.controller;

import com.backend.hotelbooking.dto.response.BookingResult;
import com.backend.hotelbooking.dto.v1.BookingRequestV1;
import com.backend.hotelbooking.dto.v1.BookingResponseV1;
import com.backend.hotelbooking.entity.BookingStatus;
import com.backend.hotelbooking.mapper.BookingV1Mapper;
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
@Tag(name = "Bookings v1", description = "Booking APIs, API version 1")
@RequiredArgsConstructor
public class BookingControllerV1 {

    private final BookingService bookingService;
    private final BookingV1Mapper mapper;

    @Operation(summary = "Create a booking (v1)")
    @PostMapping(version = "1")
    public ResponseEntity<BookingResponseV1> create(@Valid @RequestBody BookingRequestV1 request) {
        BookingResult result = bookingService.create(mapper.toCommand(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(result));
    }

    @Operation(summary = "Get a booking (v1)")
    @GetMapping(path = "/{id}", version = "1")
    public BookingResponseV1 findById(@PathVariable Long id) {
        return mapper.toResponse(bookingService.findById(id));
    }

    @Operation(summary = "List bookings (v1)")
    @GetMapping(version = "1")
    public Page<BookingResponseV1> findAll(
            @RequestParam(required = false) Long guestId,
            @RequestParam(required = false) BookingStatus status,
            @ParameterObject @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return bookingService.findAll(guestId, status, pageable).map(mapper::toResponse);
    }

    @Operation(summary = "Cancel a booking (all versions)")
    @PatchMapping(path = "/{id}/cancel", version = "1+")
    public ResponseEntity<Void> cancel(@PathVariable Long id) {
        bookingService.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
