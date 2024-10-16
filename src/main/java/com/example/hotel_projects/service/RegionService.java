package com.example.hotel_projects.service;

import com.example.hotel_projects.dto.RegionDto;
import com.example.hotel_projects.entity.RegionEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionService {
    private final ResourceBundleService resourceBundleService;
    private final RegionRepository regionRepository;

    public List<RegionDto> getAll() {
        return regionRepository.findAll().stream().map(this::toDto).toList();

    }

    public RegionDto createRegion(String name, AppLanguage appLanguage) {
        Optional<RegionEntity> optional = regionRepository.findByNameAndVisible(name, true);

        if (optional.isEmpty()) {
            RegionEntity regionEntity = new RegionEntity();
            regionEntity.setName(name);
            regionRepository.save(regionEntity);
            return toDto(regionEntity);
        }

        throw new AppBadException(resourceBundleService.getMessage("this.region.exists", appLanguage));
    }

    public RegionDto getById(Long id, AppLanguage appLanguage) {
        return toDto(getRegionById(id, appLanguage));
    }

    public RegionEntity getRegionById(Long regionId, AppLanguage appLanguage) {

        return regionRepository.findByIdAndVisible(regionId, true).orElseThrow(() -> new AppBadException(resourceBundleService.getMessage("region.not.found", appLanguage)));
    }

    private RegionDto toDto(RegionEntity regionEntity) {
        RegionDto dto = new RegionDto();

        dto.setId(regionEntity.getId());
        dto.setName(regionEntity.getName());
        dto.setVisible(regionEntity.getVisible());
        dto.setCreationDate(regionEntity.getCreationDate());
        return dto;
    }

    public RegionDto delete(Long id, AppLanguage appLanguage) {
        RegionEntity regionEntity = getRegionById(id, appLanguage);

        regionEntity.setVisible(false);
        regionRepository.save(regionEntity);

        return toDto(regionEntity);
    }

    public RegionDto update(Long regionId, RegionDto dto, AppLanguage appLanguage) {
        Optional<RegionEntity> optional = regionRepository.getByIdRegion(regionId);

        if (optional.isEmpty()) {
            throw new AppBadException(resourceBundleService.getMessage("region.not.found", appLanguage));
        }
        RegionEntity regionEntity = optional.get();

        if (dto.getId() != 0) {
            regionEntity.setId(dto.getId());
        }
        if (dto.getName() != null) {
            regionEntity.setName(dto.getName());
        }
        if (dto.getVisible() != null) {


            regionEntity.setVisible(dto.getVisible());
        }

        regionRepository.save(regionEntity);

        return toDto(regionEntity);

    }

    public PageImpl<RegionDto> pagination(Integer page, Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<RegionEntity> studentPage = regionRepository.findAll(paging);

        List<RegionEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<RegionDto> dtoList = new LinkedList<>();
        for (RegionEntity entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }

}
