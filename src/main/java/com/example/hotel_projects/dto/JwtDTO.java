package com.example.hotel_projects.dto;

import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.enums.ProfileRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtDTO {
    private String email;
    private ProfileRole role;
    private AppLanguage appLanguage;
}
