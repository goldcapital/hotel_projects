package com.example.hotel_projects.service;

import com.example.hotel_projects.dto.RegionDto;
import com.example.hotel_projects.entity.RegionEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionService {
    private final ResourceBundleService resourceBundleService;
    private final RegionRepository regionRepository;

    public RegionEntity getRegionById(Long regionId, AppLanguage appLanguage) {

        return regionRepository.findByIdAndVisible(regionId,true)
                .orElseThrow(() -> new AppBadException(resourceBundleService
                        .getMessage("region.not.found", appLanguage)));
    }

    public RegionDto createRegion(String name) {
        Optional<RegionEntity>optional=regionRepository.findByNameAndVisible(name,true);

        if(optional.isEmpty()){
            RegionEntity regionEntity=new RegionEntity();
            regionEntity.setName(name);
            regionRepository.save(regionEntity);
            return toDto(regionEntity);
        }

        return null;
    }

    private RegionDto toDto(RegionEntity regionEntity) {
        RegionDto dto=new RegionDto();

        dto.setId(regionEntity.getId());
        dto.setName(regionEntity.getName());
        dto.setVisible(regionEntity.getVisible());
        dto.setCreationDate(regionEntity.getCreationDate());
        return dto;
    }
}
