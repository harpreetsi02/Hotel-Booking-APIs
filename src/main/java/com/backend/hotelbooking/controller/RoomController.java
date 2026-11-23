package com.backend.hotelbooking.controller;

import com.backend.hotelbooking.dto.request.RoomRequest;
import com.backend.hotelbooking.dto.response.RoomResponse;
import com.backend.hotelbooking.service.RoomService;
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
@RequestMapping("/api/hotels/{hotelId}/rooms")
@Tag(name = "Rooms", description = "Room management APIs (nested under a hotel)")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @Operation(summary = "Create a room in a hotel")
    @PostMapping
    public ResponseEntity<RoomResponse> create(@PathVariable Long hotelId,
                                               @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.create(hotelId, request));
    }

    @Operation(summary = "List rooms of a hotel (paginated)")
    @GetMapping
    public ResponseEntity<Page<RoomResponse>> findAll(
            @PathVariable Long hotelId,
            @ParameterObject @PageableDefault(size = 10, sort = "roomNumber") Pageable pageable) {
        return ResponseEntity.ok(roomService.findAll(hotelId, pageable));
    }

    @Operation(summary = "Get a room of a hotel")
    @GetMapping("/{roomId}")
    public ResponseEntity<RoomResponse> findById(@PathVariable Long hotelId,
                                                 @PathVariable Long roomId) {
        return ResponseEntity.ok(roomService.findById(roomId, hotelId));
    }

    @Operation(summary = "Update a room")
    @PutMapping("/{roomId}")
    public ResponseEntity<RoomResponse> update(@PathVariable Long hotelId,
                                               @PathVariable Long roomId,
                                               @Valid @RequestBody RoomRequest request) {
        return ResponseEntity.ok(roomService.update(roomId, hotelId, request));
    }

    @Operation(summary = "Delete a room")
    @DeleteMapping("/{roomId}")
    public ResponseEntity<Void> delete(@PathVariable Long hotelId,
                                       @PathVariable Long roomId) {
        roomService.delete(roomId, hotelId);
        return ResponseEntity.noContent().build();
    }
}