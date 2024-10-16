package com.example.hotel_projects.dto;

import com.example.hotel_projects.entity.HotelEntity;
import com.example.hotel_projects.enums.RoomType;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {
    private Long id;
  //  private   String name;
    private String roomNumber;
    private Integer capacity;
    private Double price;
    private String status;
    private RoomType type;
    private Long hotel_id;
    private LocalDateTime createdAt;
}
