package com.example.hotel_projects.dto.request;

import lombok.Data;

@Data
public class HotelUpdateDto {
    private Long regionId;
    private String name;
    private Integer numberStars;
    private Boolean visible;
}
