package com.example.hotel_projects.dto.request;

import com.example.hotel_projects.dto.RoomDTO;
import com.example.hotel_projects.entity.RoomEntity;
import lombok.Data;

import java.util.List;

@Data
public class HotelCreateRequest {
    private Long regionId;
    private String name;
    private Integer numberStars;
    private Boolean visible;
    private List<RoomCreateRequest> roomList;
}
