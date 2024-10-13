package com.example.hotel_projects.service;


import com.example.hotel_projects.entity.ProfileEntity;
import com.example.hotel_projects.enums.ProfileRole;
import com.example.hotel_projects.enums.ProfileStatus;
import com.example.hotel_projects.repository.ProfileRepository;
import com.example.hotel_projects.utl.MDUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InitAminService {
    private  final ProfileRepository profileRepository;
    public void initAdmin() {
        ProfileEntity profile=new ProfileEntity();
        profile.setEmail("admin@gmail.com");
        profile.setPassword(MDUtil.encode("1234"));
        profile.setRole(ProfileRole.ROLE_ADMIN);
        profile.setName("aliy");
        profile.setStatus(ProfileStatus.ACTIVE);
        Optional<ProfileEntity>optional=profileRepository.findByEmail(profile.getEmail());
        if (optional.isEmpty()) {
            profileRepository.save(profile);
        }
    }
}
