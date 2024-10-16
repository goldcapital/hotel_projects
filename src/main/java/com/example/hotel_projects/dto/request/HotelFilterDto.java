package com.example.hotel_projects.dto.request;

import lombok.Data;

@Data
public class HotelFilterDto {
    private Long hotelId;
    private String hotelName;
    private Long regionId;
    private Integer numberStars;
}
