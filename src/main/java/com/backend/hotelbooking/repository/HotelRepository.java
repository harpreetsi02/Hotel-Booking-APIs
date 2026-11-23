package com.backend.hotelbooking.repository;

import com.backend.hotelbooking.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    boolean existsByNameIgnoreCaseAndCityIgnoreCase(String name, String city);

    boolean existsByNameIgnoreCaseAndCityIgnoreCaseAndIdNot(String name, String city, Long id);
}
