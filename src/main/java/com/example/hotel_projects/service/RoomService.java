package com.example.hotel_projects.service;

import com.example.hotel_projects.dto.RoomDTO;
import com.example.hotel_projects.dto.request.RoomCreateRequest;
import com.example.hotel_projects.entity.HotelEntity;
import com.example.hotel_projects.entity.RoomEntity;
import com.example.hotel_projects.enums.ProfileStatus;
import com.example.hotel_projects.enums.RoomType;
import com.example.hotel_projects.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class RoomService {
    private  final RoomRepository roomRepository;

    public List<RoomEntity> getRoomList(List<RoomCreateRequest> roomList, Long id) {

            List<RoomEntity>entityList=new ArrayList<>();
            for (RoomCreateRequest roomCreateRequest : roomList) {

            if (roomCreateRequest != null) {
                boolean exists=roomRepository.existsByRoomNumber(roomCreateRequest.getRoomNumber());
                if(!exists){
                    RoomEntity roomEntity = new RoomEntity();

                    HotelEntity hotelEntity=new HotelEntity();
                    hotelEntity.setId(id);

                    roomEntity.setHotel(hotelEntity);
                    roomEntity.setRoomNumber(roomCreateRequest.getRoomNumber());
                    roomEntity.setCapacity(roomCreateRequest.getCapacity());
                    roomEntity.setType(roomCreateRequest.getRoomType());
                    roomEntity.setPrice(roomCreateRequest.getPrice());
                    roomEntity.setCapacity(roomCreateRequest.getCapacity());
                    roomEntity.setStatus(ProfileStatus.ACTIVE);
                    roomEntity.setType(getRoomType(roomCreateRequest));

                    entityList.add(roomEntity);
                    roomRepository.save(roomEntity);

                }
            }
        }
        return entityList;
    }

    private  RoomType getRoomType(RoomCreateRequest roomCreateRequest){
        if (roomCreateRequest.getRoomType().equals(RoomType.COMFORT)){
            return RoomType.COMFORT;
        }
        if (roomCreateRequest.getRoomType().equals(RoomType.LUX)){
            return RoomType.LUX;
        }
        return RoomType.STANDARD;
    }
    private RoomDTO toRoomDto(RoomEntity roomEntity) {

        RoomDTO roomDto = new RoomDTO();
        roomDto.setId(roomEntity.getId());
        roomDto.setRoomNumber(roomEntity.getRoomNumber());
        roomDto.setType(roomEntity.getType());
        roomDto.setType(roomEntity.getType());
        roomDto.setPrice(roomEntity.getPrice());
        roomDto.setCapacity(roomDto.getCapacity());
        roomDto.setCreatedAt(roomEntity.getCreationDate());
        roomDto.setStatus(String.valueOf(roomEntity.getStatus()));
        return roomDto;
    }

    public List<RoomDTO> getRoomEntityList(HotelEntity entity) {

        if (entity.getRoomList() == null) {
            return Collections.emptyList();
        }
        return entity.getRoomList().stream()
                .map(this::toRoomDto)
                .toList();
    }
}
