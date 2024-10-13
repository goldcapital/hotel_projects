package com.example.hotel_projects.dto;

import com.example.hotel_projects.dto.request.RoomCreateRequest;
import com.example.hotel_projects.entity.RoomEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDTO {
    private Long hotelId;
    private Boolean visible;
    private String hotelName;
    private Long regionId;
    private Integer numberStars;
    private List<RoomDTO> roomList;
}
