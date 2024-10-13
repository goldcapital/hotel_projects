package com.example.hotel_projects.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RegionDto {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private Boolean visible;
}
