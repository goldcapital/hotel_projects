package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "reservation")
public class ReservationEntity extends PersonEntity {

     @ManyToOne(fetch = FetchType.LAZY)
     @JoinColumn(name = "guest_id",nullable = false)
    private ProfileEntity guestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id",nullable = false)
    private RoomEntity roomId;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

}
