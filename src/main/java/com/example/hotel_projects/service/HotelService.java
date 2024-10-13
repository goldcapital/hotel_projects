package com.example.hotel_projects.service;

import com.example.hotel_projects.dto.HotelDTO;
import com.example.hotel_projects.dto.request.HotelCreateRequest;
import com.example.hotel_projects.entity.HotelEntity;
import com.example.hotel_projects.entity.RegionEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.HotelRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class HotelService {
    private final RegionService regionService;
    private final HotelRepository hotelRepository;
    private final RoomService roomService;
    private final ResourceBundleService resourceBundleService;

    public HotelDTO createHotel(@Valid HotelCreateRequest hotelDTO, AppLanguage appLanguage) {
        regionService.getRegionById(hotelDTO.getRegionId(), appLanguage);
        hotelRepository.save(toEntity(hotelDTO));
        return toDto(toEntity(hotelDTO));

    }

    public Boolean delete(String name, AppLanguage appLanguage) {

        return hotelRepository.findByName(name)
                .map(hotelEntity -> {
                    hotelRepository.updateByName(name,false);
                    return true;
                })
                .orElseThrow(() ->
                        new AppBadException(resourceBundleService
                                .getMessage("no.hotel.with.this.name.was.found", appLanguage)));

    }

    public HotelDTO update(String name, HotelCreateRequest request, AppLanguage appLanguage) {

        HotelEntity hotelEntity=hotelRepository.findByName(name)
                .orElseThrow(()->new AppBadException(resourceBundleService.getMessage("no.hotel.with.this.name.was.found",appLanguage)));


            RegionEntity regionEntity=new RegionEntity();
            regionEntity.setId(request.getRegionId());

            hotelEntity.setName(request.getName());
            hotelEntity.setNumberStars(request.getNumberStars());
            hotelEntity.setRegionId(regionEntity);
            hotelEntity.setVisible(request.getVisible());
            hotelEntity.setRoomList(roomService.getRoomList(request.getRoomList(),hotelEntity.getId()));

            return toDto(hotelRepository.save(hotelEntity));
    }

    private HotelDTO toDto(HotelEntity entity) {

        HotelDTO dto = new HotelDTO();

        dto.setHotelId(entity.getId());
        dto.setHotelName(entity.getName());
        dto.setRegionId(entity.getRegionId().getId());
        dto.setNumberStars(entity.getNumberStars());

        dto.setRoomList(roomService.getRoomEntityList(entity));
        return dto;

    }

    private HotelEntity toEntity(HotelCreateRequest request) {
        HotelEntity entity = new HotelEntity();

        RegionEntity regionEntity=new RegionEntity();
        regionEntity.setId(request.getRegionId());

        entity.setName(request.getName());
        entity.setNumberStars(request.getNumberStars());
        entity.setRegionId(regionEntity);
        entity.setRoomList(roomService.getRoomList(request.getRoomList(),entity.getId()));
        return entity;

    }

    public List<HotelDTO> getAll() {
        List<HotelEntity>hotelEntityList=
               hotelRepository.
                        findByVisible(true);
                            return hotelEntityList.stream()
                                            .map(this::toDto).toList();
    }

    public HotelDTO getByName(String name, AppLanguage appLanguage) {
        return hotelRepository.findByName(name)
                .map(this::toDto).orElseThrow(()
                        ->new AppBadException(resourceBundleService
                        .getMessage("no.hotel.with.this.name.was.found",appLanguage)));
    }
}
