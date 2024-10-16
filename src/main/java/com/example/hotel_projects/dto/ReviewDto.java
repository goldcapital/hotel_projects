package com.example.hotel_projects.dto;

import com.example.hotel_projects.entity.ReservationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private Long reservationId;
    private ReservationEntity reservation;
    private Integer rating;
    private String comment;
    private LocalDateTime createdDate;
}
