package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class ReviewEntity  extends PersonEntity {

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private ProfileEntity customer;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;

    @Column(name = "rating", nullable = false)
    private Integer rating; // Baholash reytingi (masalan, 1 dan 5 gacha)

    @Column(name = "comment",columnDefinition = "TEXT")
    private String comment; // Baholash uchun izoh
}
