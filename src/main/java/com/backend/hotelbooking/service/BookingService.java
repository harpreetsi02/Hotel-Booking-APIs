package com.backend.hotelbooking.service;

import com.backend.hotelbooking.dto.response.BookingCommand;
import com.backend.hotelbooking.dto.response.BookingResult;
import com.backend.hotelbooking.entity.*;
import com.backend.hotelbooking.exception.BookingConflictException;
import com.backend.hotelbooking.exception.InvalidBookingException;
import com.backend.hotelbooking.exception.ResourceNotFoundException;
import com.backend.hotelbooking.repository.BookingRepository;
import com.backend.hotelbooking.repository.GuestRepository;
import com.backend.hotelbooking.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;

    @Transactional
    public BookingResult create(BookingCommand cmd) {
        Room room = roomRepository.findById(cmd.roomId())
                .orElseThrow(() -> new ResourceNotFoundException("Room", cmd.roomId()));
        Guest guest = guestRepository.findById(cmd.guestId())
                .orElseThrow(() -> new ResourceNotFoundException("Guest", cmd.guestId()));

        if (!cmd.checkInDate().isBefore(cmd.checkOutDate())) {
            throw new InvalidBookingException("Check-out date must be after check-in date");
        }
        if (cmd.checkInDate().isBefore(LocalDate.now())) {
            throw new InvalidBookingException("Check-in date cannot be in the past");
        }
        if (!room.isAvailable()) {
            throw new BookingConflictException("Room " + room.getRoomNumber() + " is not available for booking");
        }
        if (cmd.numberOfGuests() > room.getCapacity()) {
            throw new InvalidBookingException("Room capacity is " + room.getCapacity()
                    + " but " + cmd.numberOfGuests() + " guests requested");
        }
        if (bookingRepository.countOverLapping(room.getId(), BookingStatus.CONFIRMED,
                cmd.checkInDate(), cmd.checkOutDate()) > 0) {
            throw new BookingConflictException("Room is already booked for the selected dates");
        }

        long nights = ChronoUnit.DAYS.between(cmd.checkInDate(), cmd.checkOutDate());
        BigDecimal total = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));

        Booking booking = Booking.builder()
                .room(room)
                .guest(guest)
                .checkInDate(cmd.checkInDate())
                .checkOutDate(cmd.checkOutDate())
                .numberOfGuests(cmd.numberOfGuests())
                .pricePerNight(room.getPricePerNight())   // snapshot
                .totalAmount(total)
                .build();

        return toResult(bookingRepository.save(booking));
    }

    @Transactional(readOnly = true)
    public BookingResult findById(Long bookingId) {
        return toResult(findBookingOrThrow(bookingId));
    }

    @Transactional(readOnly = true)
    public Page<BookingResult> findAll(Long guestId, BookingStatus status, Pageable pageable) {
        return bookingRepository.search(guestId, status, pageable).map(this::toResult);
    }

    @Transactional
    public void cancel(Long bookingId) {
        Booking booking = findBookingOrThrow(bookingId);
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingConflictException("Booking " + bookingId + " is already cancelled");
        }
        booking.setStatus(BookingStatus.CANCELLED); // dirty checking
    }

    private Booking findBookingOrThrow(Long bookingId) {
        return bookingRepository.findByIdWithDetails(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", bookingId));
    }

    private BookingResult toResult(Booking b) {
        Room room = b.getRoom();
        Hotel hotel = room.getHotel();
        Guest guest = b.getGuest();
        long nights = ChronoUnit.DAYS.between(b.getCheckInDate(), b.getCheckOutDate());
        return new BookingResult(
                b.getId(),
                hotel.getId(), hotel.getName(), hotel.getCity(),
                room.getId(), room.getRoomNumber(), room.getType(),
                guest.getId(), guest.getFullName(), guest.getEmail(),
                b.getCheckInDate(), b.getCheckOutDate(), nights,
                b.getNumberOfGuests(),
                b.getPricePerNight(), b.getTotalAmount(),
                b.getStatus(), b.getCreatedAt());
    }
}
