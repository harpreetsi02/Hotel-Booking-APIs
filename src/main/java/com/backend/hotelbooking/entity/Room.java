package com.backend.hotelbooking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "rooms",
        uniqueConstraints = @UniqueConstraint(columnNames = {"hotel_id", "roomNumber"})
)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "room_number", nullable = false)
    private Integer roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoomType type;

    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerNight;

    @Column(nullable = false)
    private Integer capacity;

    @Builder.Default
    @Column(nullable = false)
    private boolean available = true;
}
