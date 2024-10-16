package com.example.hotel_projects.dto.request;

import com.example.hotel_projects.entity.ProfileEntity;
import com.example.hotel_projects.entity.RoomEntity;
import lombok.Data;


import java.time.LocalDate;
@Data
public class ReservationRequestDto {
    private Long roomId;
    private Long hotelId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
}
