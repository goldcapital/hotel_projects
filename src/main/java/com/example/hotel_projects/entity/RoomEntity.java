package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import com.example.hotel_projects.enums.ProfileStatus;
import com.example.hotel_projects.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "room")
public class RoomEntity  extends PersonEntity {

    @Column(name = "room_number", nullable = false,unique = true)
    private String roomNumber;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProfileStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RoomType type;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private HotelEntity hotel;
}
