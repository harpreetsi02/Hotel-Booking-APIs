package com.backend.hotelbooking.repository;

import com.backend.hotelbooking.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    boolean existsByHotel_Id(Long hotelId);

    boolean existsByHotel_IdAndRoomNumber(Long hotelId, Integer roomNumber);

    boolean existsByHotel_IdAndRoomNumberAndIdNot(Long hotelId, Integer roomNumber, Long id);

    @Query("select r from Room r join fetch r.hotel h where r.id = :roomId and h.id = :hotelId")
    Optional<Room> findByIdAndHotelIdWithHotel(
            @Param("roomId") Long roomId,
            @Param("hotelId") Long hotelId
    );

    @Query(value = "select r from Room r join fetch r.hotel h where h.id = :hotelId",
            countQuery = "select count(r) from Room r where r.hotel.id = :hotelId")
    Page<Room> findAllByHotelIdWithHotel(@Param("hotelId") Long hotelId, Pageable pageable);
}
