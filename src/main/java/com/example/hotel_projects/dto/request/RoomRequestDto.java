package com.example.hotel_projects.dto.request;

import com.example.hotel_projects.enums.RoomType;
import lombok.Data;

@Data
public class RoomRequestDto {
    private Long hotelId;
    private String roomNumber;
    private Integer capacity;
    private Double price;
    private RoomType roomType;
}
