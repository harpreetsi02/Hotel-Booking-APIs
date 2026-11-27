package com.backend.hotelbooking.repository;

import com.backend.hotelbooking.entity.Booking;
import com.backend.hotelbooking.entity.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByRoom_Id(Long roomId);

    boolean existsByGuest_Id(Long guestId);

    @Query("""
            SELECT COUNT(b) FROM Booking b
            WHERE b.room.id = :roomId
                AND b.status = :status
                AND b.checkInDate < :checkOut
                AND b.checkOutDate > :checkIn
            """)
    long countOverLapping(@Param("roomId") Long roomId,
                          @Param("status") BookingStatus status,
                          @Param("checkIn") LocalDate checkIn,
                          @Param("checkOut") LocalDate checkOut);

    @Query("""
        SELECT b FROM Booking b
                JOIN FETCH b.room r
                JOIN FETCH r.hotel
                JOIN FETCH b.guest
        WHERE b.id = :id
        """)
    Optional<Booking> findByIdWithDetails(@Param("id") Long id);

    @Query(value = """
        SELECT b FROM Booking b
                JOIN FETCH b.room r
                JOIN FETCH r.hotel
                JOIN FETCH b.guest g
        WHERE (:guestId IS NULL OR g.id = :guestId)
                    AND (:status IS NULL OR b.status = :status)
        """,

    countQuery = """
            SELECT COUNT(b) FROM Booking b
            WHERE (:guestId IS NULL OR b.guest.id = :guestId)
            AND (:status IS NULL OR b.status = :status)
            """)
    Page<Booking> search(@Param("guestId") Long guestId,
                         @Param("status") BookingStatus status,
                         Pageable pageable);
}
