package com.backend.hotelbooking.controller;

import com.backend.hotelbooking.dto.request.GuestRequest;
import com.backend.hotelbooking.dto.response.GuestResponse;
import com.backend.hotelbooking.service.GuestService;
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

import java.util.List;

@RestController
@RequestMapping("/api/guests")
@Tag(name = "Guest's", description = "Guest management APIs")
@RequiredArgsConstructor
public class GuestController {

    private final GuestService guestService;

    @Operation(summary = "Create a guest")
    @PostMapping
    public ResponseEntity<GuestResponse> create(
            @Valid @RequestBody GuestRequest request
    ) {
        GuestResponse response =
                guestService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "List all guests")
    @GetMapping
    public ResponseEntity<Page<GuestResponse>> findAll(
            @ParameterObject @PageableDefault(size = 10, sort = "fullName") Pageable pageable
    ) {
        Page<GuestResponse> responses =
                guestService.findAll(pageable);

        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Find a guest")
    @GetMapping("/{guestId}")
    public ResponseEntity<GuestResponse> findById(
            @PathVariable Long guestId
    ) {
        GuestResponse response =
                guestService.findById(guestId);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update a guest")
    @PutMapping("/{guestId}")
    public ResponseEntity<GuestResponse> update(
            @PathVariable Long guestId,
            @Valid @RequestBody GuestRequest request
    ) {
        GuestResponse response =
                guestService.update(guestId, request);

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete a guest")
    @DeleteMapping("/{guestId}")
    public ResponseEntity<Void> delete(
            @PathVariable Long guestId
    ) {
        guestService.deleteById(guestId);
        return ResponseEntity.noContent().build();
    }
}
