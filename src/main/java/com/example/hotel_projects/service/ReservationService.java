package com.example.hotel_projects.service;

import com.example.hotel_projects.dto.ReservationDto;
import com.example.hotel_projects.dto.request.ReservationRequestDto;
import com.example.hotel_projects.dto.request.ReturnUserReservationsDto;
import com.example.hotel_projects.entity.ReservationEntity;
import com.example.hotel_projects.entity.RoomEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.enums.Status;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.ReservationRepository;
import com.example.hotel_projects.utl.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomService roomService;
    private final ProfileService profileService;
    private final ResourceBundleService resourceBundleService;

    public ReservationDto createReservation(ReservationRequestDto dto, AppLanguage appLanguage) {

        RoomEntity entity = roomService.getRoomByHotelIdAndRoomId(dto.getHotelId(), dto.getRoomId(), appLanguage);

        if (entity != null && entity.getStatus().equals(Status.ACTIVE) && entity.getVisible()) {
            if (profileService.getByIdAndIsLoggedIn(SpringSecurityUtil.getCurrentUser().getEmail(), appLanguage)) {
                if (roomService.updateRoomNumberAndHotelIdAndStatus(entity.getRoomNumber(), dto.getHotelId(), Status.NOT_ACTIVE, appLanguage)) {
                    reservationRepository.save(toEntity(dto));
                    profileService.setVerificationProfile(dto.getHotelId(), entity.getRoomNumber(), SpringSecurityUtil.getCurrentUser());

                    return toDto(toEntity(dto));
                }
            }
            throw new AppBadException(resourceBundleService.getMessage("you.are.locked.out.please.login.again", appLanguage));
        }
        throw new AppBadException(resourceBundleService.getMessage("this.room.is.busy.or.not.working", appLanguage));
    }

    public ReservationEntity toEntity(ReservationRequestDto dto) {
        ReservationEntity entity = new ReservationEntity();

        entity.setGuestId(SpringSecurityUtil.getCurrentUser().getId());
        entity.setRoomId(dto.getRoomId());
        entity.setHotelId(dto.getHotelId());
        entity.setCheckInDate(dto.getCheckInDate());
        entity.setCheckOutDate(dto.getCheckOutDate());
        return entity;
    }


    public List<ReservationDto> getAllReservation(Long hotelId) {
        return reservationRepository.getBYAndHotelIdAndVisible(true,hotelId)
                .stream()
                .map(this::toDto)
                .toList();
    }

    private ReservationDto toDto(ReservationEntity reservationEntity) {
        ReservationDto reservationDto = new ReservationDto();

        reservationDto.setId(reservationDto.getId());
        reservationDto.setCheckInDate(reservationEntity.getCheckInDate());
        reservationDto.setCheckOutDate(reservationEntity.getCheckOutDate());
        reservationDto.setGuestId(reservationEntity.getGuestId());
        reservationDto.setRoomId(reservationDto.getRoomId());
        reservationDto.setHotelId(reservationDto.getHotelId());
        return reservationDto;

    }

    public ReservationDto getById(Long id, AppLanguage appLanguage) {
        return reservationRepository.findByIdAndProfileId(id, SpringSecurityUtil.getCurrentUser().getId()).map(this::toDto).orElseThrow(()
                -> new AppBadException(resourceBundleService.getMessage("item.not.found", appLanguage)));
    }

    public Boolean delete(Long reservationId, AppLanguage appLanguage) {

        Optional<ReservationEntity> reservation = reservationRepository.findById(reservationId);

        if (reservation.isPresent()) {
            reservationRepository.updateByIdAndVisible(reservationId, false);
            return true;
        }
        throw new AppBadException(resourceBundleService.getMessage("item.not.found", appLanguage));
    }

    public ReservationDto returnUserReservations(ReturnUserReservationsDto dto, AppLanguage appLanguage) {

        Optional<ReservationEntity> optional = reservationRepository.findByIdAndHotelIdAndRoomIdAndGuestId(
                dto.getReservationId(),
                dto.getHotelId(),
                dto.getRoomId(),
                SpringSecurityUtil.getCurrentUser().getId());
        if (optional.isPresent()) {
            if (profileService.getByIdAndIsLoggedIn(SpringSecurityUtil.getCurrentUser().getEmail(), appLanguage)) {
                roomService.updateRoomIdAndHotilId(dto.getHotelId(), dto.getRoomId(), Status.ACTIVE);
            }
        }
        throw new AppBadException(resourceBundleService.getMessage("item.not.found", appLanguage));
    }

    public PageImpl<ReservationDto> pagination(Long hotelId, Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "creationDate");
        Pageable paging = PageRequest.of(page - 1, size, sort);

        Page<ReservationEntity> studentPage = reservationRepository.findAllByHotelId(hotelId,paging);

        List<ReservationEntity> entityList = studentPage.getContent();
        Long totalElements = studentPage.getTotalElements();

        List<ReservationDto> dtoList = new LinkedList<>();
        for (ReservationEntity entity : entityList) {
            dtoList.add(toDto(entity));
        }
        return new PageImpl<>(dtoList, paging, totalElements);

    }

    public ReservationDto updateReservation(Long id, ReservationDto dto,AppLanguage appLanguage) {

        Optional<ReservationEntity>optional=reservationRepository.findById(id);
        if (optional.isPresent()){
            ReservationEntity reservationEntity=optional.get();
            if (dto.getRoomId()!=0) {
                reservationEntity.setRoomId(dto.getRoomId());
            }
            if (dto.getHotelId()!=0) {
                reservationEntity.setHotelId(dto.getHotelId());
            }
            if (dto.getCheckInDate()!=null) {
                reservationEntity.setCheckInDate(dto.getCheckInDate());
            }
            if (dto.getCheckOutDate()!=null) {
                reservationEntity.setCheckOutDate(dto.getCheckOutDate());
            }

            reservationRepository.save(reservationEntity);
            return toDto(reservationEntity);
        }
        throw new AppBadException(resourceBundleService.getMessage("item.not.found",appLanguage));
    }

    public boolean getByReservationId(Long reservationId, AppLanguage appLanguage) {
        return  reservationRepository.findById(reservationId).map(reservationEntity -> {
            return true;
        }).orElseThrow(()->new AppBadException(resourceBundleService.getMessage("item.not.found",appLanguage)));
    }
}
