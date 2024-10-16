package com.example.hotel_projects.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDto {
    private Long id;
    private String name;
    private LocalDateTime creationDate;
    private Boolean visible;
}
