package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.HotelEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends CrudRepository<HotelEntity,Long> {
    Optional<HotelEntity> deleteByName(String name);

    Optional<HotelEntity> findByName(String name);
@Transactional
@Modifying
@Query("update  HotelEntity  set visible= :b where name= :name")
    void updateByName(@Param("name") String name,@Param("b") boolean b);

    List<HotelEntity> findByVisible(boolean b);
}
