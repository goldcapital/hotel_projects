package com.example.hotel_projects.entity;

import com.example.hotel_projects.entity.person.PersonEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "reviews")
public class ReviewEntity extends PersonEntity {

    @Column(name = "reservation_id",nullable = false)
    private Long reservationId;
    @ManyToOne
    @JoinColumn(name = "reservation_id", insertable = false, updatable = false)
    private ReservationEntity reservation;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
}
