package com.example.hotel_projects.repository;

import com.example.hotel_projects.dto.RoomDTO;
import com.example.hotel_projects.entity.RoomEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoomRepository extends  JpaRepository<RoomEntity,Long> {
    Optional<RoomEntity> findByHotelIdAndRoomNumber(Long hotelId, String roomNumber);
    boolean existsByHotelIdAndRoomNumber(Long id, String roomNumber);


    Optional<RoomEntity> findByRoomNumber(String number);
@Transactional
@Modifying
@Query("update  RoomEntity r set r.visible=false where r.hotel.id =:hotelId and r.roomNumber =:number")
    Boolean deleteByHotelIdAndRoomNumber(@Param("hotelId") Long hotelId,@Param("number") String number);
}
