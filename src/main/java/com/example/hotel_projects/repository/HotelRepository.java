package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.HotelEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface HotelRepository extends JpaRepository<HotelEntity, Long> {


    @Query("from  HotelEntity  where name =:name")
    Optional<HotelEntity> findByName(@Param("name") String name);

    @Transactional
    @Modifying
    @Query("update  HotelEntity  set visible= :b where name= :name")
    void updateByName(@Param("name") String name, @Param("b") boolean b);

    List<HotelEntity> findByVisible(boolean b);

    Page<HotelEntity> findAllByVisible(boolean b, Pageable paging);
}
