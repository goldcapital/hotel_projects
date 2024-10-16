package com.example.hotel_projects.dto.request;

import lombok.Data;

@Data
public class ReturnUserReservationsDto {
    private Long reservationId;
    private Long roomId;
    private Long hotelId;
}
