package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "reservation")
public class ReservationEntity extends PersonEntity {


    @Column(name = "check_in_date",nullable = false)
    private LocalDate checkInDate;

    @Column(name = "check_out_date",nullable = false)
    private LocalDate checkOutDate;

    @Column(name = "profile_id",nullable = false)
    private String guestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;

    @Column(name = "hotel_id",nullable = false)
    private Long hotelId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotele_id", insertable = false, updatable = false)
    private HotelEntity hotel;

    @Column(name = "room_id",nullable = false)
    private Long roomId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", insertable = false, updatable = false)
    private RoomEntity room;


}
