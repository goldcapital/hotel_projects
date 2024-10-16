package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.RoomEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
   // @Query("from RoomEntity where hotel_id =:hotelId and id =:roomId")
    Optional<RoomEntity> findByHotel_idAndId(Long hotelId, Long rooId);

    boolean existsByHotel_idAndRoomNumber(Long id, String roomNumber);


    Optional<RoomEntity> findByRoomNumber(String number);

    @Transactional
    @Modifying
    @Query("update  RoomEntity r set r.visible=false where r.hotel_id =:hotelId and r.roomNumber =:number")
    Boolean deleteByHotelIdAndRoomNumber(@Param("hotelId") Long hotelId, @Param("number") String number);

    Optional<RoomEntity> findByHotel_idAndRoomNumber(Long hotelId, String roomNumber);

    Page<RoomEntity> findAllByHotel_id(Long hotelId, Pageable paging);

    List<RoomEntity> findAllByVisibleAndHotel_id(boolean b, Long hotelId);
}
