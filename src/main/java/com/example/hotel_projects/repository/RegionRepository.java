package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.RegionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RegionRepository extends CrudRepository<RegionEntity,Long> {
    Optional<RegionEntity> findByNameAndVisible(String name, boolean b);

    Optional<RegionEntity> findByIdAndVisible(Long regionId, boolean b);
}
