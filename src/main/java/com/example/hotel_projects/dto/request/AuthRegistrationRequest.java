package com.example.hotel_projects.dto.request;

import lombok.Data;

@Data
public class AuthRegistrationRequest {
    private String username;
    private String email;
    private String password;

}
