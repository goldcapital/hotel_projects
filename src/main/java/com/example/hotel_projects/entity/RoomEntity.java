package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import com.example.hotel_projects.enums.Status;
import com.example.hotel_projects.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "room")
public class RoomEntity extends PersonEntity {

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "price")
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RoomType type;

    @Column(name = "hotel_id",nullable = false)
    private Long hotel_id;
    @ManyToOne
    @JoinColumn(name = "hotel_id",insertable = false, updatable = false)
    private HotelEntity hotel;

}
