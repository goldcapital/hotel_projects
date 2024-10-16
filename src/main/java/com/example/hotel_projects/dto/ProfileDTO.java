package com.example.hotel_projects.dto;



import com.example.hotel_projects.enums.ProfileRole;
import com.example.hotel_projects.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProfileDTO {
    private UUID uuid;
    private  String email;
    private String name;
    private  String password;
    private ProfileRole role;
    private Status status;
    private  String jwtToken;
}
