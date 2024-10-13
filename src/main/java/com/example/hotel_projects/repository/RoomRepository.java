package com.example.hotel_projects.repository;

import com.example.hotel_projects.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<RoomEntity,Long> {

    boolean existsByRoomNumber(String roomNumber);
}
