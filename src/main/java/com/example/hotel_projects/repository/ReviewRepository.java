package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {
    @Query("select r from ReviewEntity r " +
            "inner join ReservationEntity ro on r.reservationId = ro.id " +
            "inner join ProfileEntity p on p.id = ro.guestId " +
            "where p.id = :profileId and r.visible = true ")
    List<ReviewEntity> findAllByProfileId(@Param("profileId") String id);

    @Query("select r from ReviewEntity r " +
            "inner join ReservationEntity ro on r.reservationId = ro.id " +
            "inner join ProfileEntity p on p.id = ro.guestId " +
            "where p.id = :profileId and r.id =:reviewId")
    Optional<ReviewEntity> getByProfileIdAndReviewId(@Param("profileId") String profileId ,@Param("reviewId") Long reviewId);
}
