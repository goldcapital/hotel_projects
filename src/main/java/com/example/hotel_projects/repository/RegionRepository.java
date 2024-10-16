package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RegionRepository extends JpaRepository<RegionEntity, Long>,CrudRepository<RegionEntity,Long> {
    Optional<RegionEntity> findByNameAndVisible(String name, boolean b);

    Optional<RegionEntity> findByIdAndVisible(Long regionId, boolean b);

    @Query("from RegionEntity  where id =:regionId")
   Optional< RegionEntity >getByIdRegion(@Param("regionId") Long regionId);
}
