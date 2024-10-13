package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
@Data
@Entity
@Table(name = "reservation")
public class ReservationEntity extends PersonEntity {


    @Column(name = "guest_id", nullable = false)
    private Long guestId;

    @Column(name = "room_id", nullable = false)
    private Long roomId;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

}
