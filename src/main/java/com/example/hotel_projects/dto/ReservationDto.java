package com.example.hotel_projects.dto;


import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Long id;
    private Long hotelId;
    private String guestId;
    private Long roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime creationDate;
}
