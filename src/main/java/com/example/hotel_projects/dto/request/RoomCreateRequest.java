package com.example.hotel_projects.dto.request;

import com.example.hotel_projects.entity.HotelEntity;
import com.example.hotel_projects.enums.RoomType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomCreateRequest {
    private String roomNumber;
    private Integer capacity;
    private Double price;
    private RoomType roomType;

}
