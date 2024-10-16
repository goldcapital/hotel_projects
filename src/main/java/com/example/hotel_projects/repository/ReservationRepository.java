package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.ReservationEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {


    @Query("select r from ReservationEntity r " +
            "inner join RoomEntity ro on r.roomId = ro.id " +
            "inner join HotelEntity h on ro.hotel.id = h.id " +
            "where h.id = :hotelId and r.visible = :visible")
    List<ReservationEntity> getBYAndHotelIdAndVisible(@Param("visible") boolean b,@Param("hotelId") Long hotelId);

    @Query("from  ReservationEntity r where r.id =:id and r.guestId =:profileId  ")
    Optional<ReservationEntity> findByIdAndProfileId(@Param("id") Long id, @Param("profileId") String profileId);

    @Transactional
    @Modifying
    @Query("update  ReservationEntity set visible =:visible where id =:reservationId")
    void updateByIdAndVisible(@Param("reservationId") Long reservationId, @Param("visible") boolean b);

    Optional<ReservationEntity> findByIdAndHotelIdAndRoomIdAndGuestId(Long reservationId, Long hotelId, Long roomId, String id);

    Page<ReservationEntity> findAllByHotelId(Long hotelId,Pageable paging);
}
