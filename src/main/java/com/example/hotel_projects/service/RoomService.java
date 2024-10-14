package com.example.hotel_projects.service;

import com.example.hotel_projects.dto.RoomDTO;
import com.example.hotel_projects.dto.request.RoomCreateRequest;
import com.example.hotel_projects.dto.request.RoomRequestDto;
import com.example.hotel_projects.entity.HotelEntity;
import com.example.hotel_projects.entity.RoomEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.enums.ProfileStatus;
import com.example.hotel_projects.enums.RoomType;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;
    private final ResourceBundleService resourceBundleService;


    public RoomDTO createRoom(RoomRequestDto dto, AppLanguage appLanguage) {

        return (RoomDTO) roomRepository.findByHotelIdAndRoomNumber(dto.getHotelId(), dto.getRoomNumber())
                .map(room -> {
                    throw new AppBadException(resourceBundleService
                            .getMessage("this.hotel.has.this.digital.room",appLanguage));
                })
                            . orElseGet(() -> toRoomDto(roomRepository.save(toEntity(dto))));
    }
    public List<RoomDTO> getRoomAll() {
       return roomRepository.findAll()
               .stream()
               .map(this::toRoomDto)
               .toList();
    }

    public RoomDTO getRoomByNumber(String number, AppLanguage appLanguage) {

       return roomRepository.findByRoomNumber(number).map(this::toRoomDto)
               .orElseThrow(()->new AppBadException(resourceBundleService
                       .getMessage("this.hotel.has.this.digital.room",appLanguage)));
    }

    public List<RoomEntity> getRoomList(List<RoomCreateRequest> roomList, Long id) {

        List<RoomEntity> entityList = new ArrayList<>();
        for (RoomCreateRequest roomCreateRequest : roomList) {

            if (roomCreateRequest != null) {
                boolean exists = roomRepository.existsByHotelIdAndRoomNumber(id,roomCreateRequest.getRoomNumber());
                if (!exists) {
                    RoomEntity roomEntity = new RoomEntity();

                    HotelEntity hotelEntity = new HotelEntity();
                    hotelEntity.setId(id);

                    roomEntity.setHotel(hotelEntity);
                    roomEntity.setRoomNumber(roomCreateRequest.getRoomNumber());
                    roomEntity.setCapacity(roomCreateRequest.getCapacity());
                    roomEntity.setType(roomCreateRequest.getRoomType());
                    roomEntity.setPrice(roomCreateRequest.getPrice());
                    roomEntity.setStatus(ProfileStatus.ACTIVE);


                    entityList.add(roomEntity);
                    roomRepository.save(roomEntity);

                }
            }
        }
        return entityList;
    }

    private RoomType getRoomType(RoomType roomCreateRequest) {
        if (roomCreateRequest.equals(RoomType.COMFORT)) {
            return RoomType.COMFORT;
        }
        if (roomCreateRequest.equals(RoomType.LUX)) {
            return RoomType.LUX;
        }
        return RoomType.STANDARD;
    }

    public List<RoomDTO> getRoomEntityList(HotelEntity entity) {

        if (entity.getRoomList() == null) {
            return Collections.emptyList();
        }
        return entity.getRoomList().stream()
                .map(this::toRoomDto)
                .toList();
    }

    public Boolean updateRoom(String roomNumber, RoomDTO dto, Long hotelId, AppLanguage appLanguage) {
        return  roomRepository.findByHotelIdAndRoomNumber(hotelId,roomNumber).
                map(roomEntity -> {

                    roomEntity.setRoomNumber(dto.getRoomNumber());
                    roomEntity.setCapacity(dto.getCapacity());
                    roomEntity.setType(dto.getType());
                    roomEntity.setPrice(dto.getPrice());
                    return  true;
                })
                .orElseThrow(()->new AppBadException(resourceBundleService.getMessage("this.hotel.has.this.digital.room",appLanguage)));
    }

    public Boolean deleteRoom(String number, Long hotelId, AppLanguage appLanguage) {
        return  roomRepository.findByHotelIdAndRoomNumber(hotelId,number).map
                (roomEntity-> {
                    roomRepository.deleteByHotelIdAndRoomNumber(hotelId, number);
                    return true;
                } ).orElseThrow(()->new AppBadException(resourceBundleService.getMessage("this.hotel.has.this.digital.room",appLanguage)));


    }

    public PageImpl<RoomDTO> pagination(Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<RoomEntity> studentPage = roomRepository.findAll(paging);

        List<RoomEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<RoomDTO> dtoList = new LinkedList<>();
        for (RoomEntity entity : entityList) {
            dtoList.add(toRoomDto(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElements);
    }

    private RoomEntity toEntity(RoomRequestDto dto){

        HotelEntity hotelEntity = new HotelEntity();
        hotelEntity.setId(dto.getHotelId());

        RoomEntity roomEntity=new RoomEntity();

        roomEntity.setHotel(hotelEntity);
        roomEntity.setRoomNumber(dto.getRoomNumber());
        roomEntity.setCapacity(dto.getCapacity());
        roomEntity.setType(dto.getRoomType());
        roomEntity.setPrice(dto.getPrice());
        roomEntity.setStatus(ProfileStatus.ACTIVE);
        roomEntity.setType(getRoomType(dto.getRoomType()));

        return roomEntity;
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
}
