package com.example.hotel_projects.dto.request;

import com.example.hotel_projects.entity.ReservationEntity;
import lombok.Data;

@Data
public class ReviewRequestDto {

    private Long reservationId;
    private ReservationEntity reservation;
    private Integer rating;
    private String comment;
}
