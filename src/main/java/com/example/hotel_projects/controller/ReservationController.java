package com.example.hotel_projects.controller;

import com.example.hotel_projects.dto.ReservationDto;
import com.example.hotel_projects.dto.request.ReservationRequestDto;
import com.example.hotel_projects.dto.request.ReturnUserReservationsDto;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<ReservationDto> creteReservation(@RequestBody ReservationRequestDto dto,
                                                           @RequestHeader(value = "Accept-Language",
                                                                   defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(reservationService.createReservation(dto, appLanguage));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Api for Reservation  admin get all", description = "by this api  to get all Reservation")
    @GetMapping("/get_all/{hotelId}")
    public ResponseEntity<List<ReservationDto>> getAllReservation(@PathVariable Long hotelId) {
        return ResponseEntity.ok(reservationService.getAllReservation(hotelId));
    }

    @GetMapping("/get_by_id{id}")
    @Operation(summary = "Api for ordering from the requested user", description = "only the user can retrieve the order related to him by the order id")
    public ResponseEntity<ReservationDto> getById(@PathVariable("id") Long id,
                                                  @RequestHeader(value = "Accept-Language",
                                                          defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(reservationService.getById(id, appLanguage));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{reservationId}")
    public ResponseEntity<Boolean> deleteReservation(@PathVariable Long reservationId,
                                                     @RequestHeader(value = "Accept-Language",
                                                             defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(reservationService.delete(reservationId, appLanguage));
    }

    @PutMapping("/return/{reservationId}")
    public ResponseEntity<ReservationDto> returnUserReservations(@RequestBody ReturnUserReservationsDto dto,
                                                                 @RequestHeader(value = "Accept-Language",
                                                                         defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(reservationService.returnUserReservations(dto, appLanguage));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservationDto> updateReservation(@PathVariable Long id, @RequestBody ReservationDto dto,
                                                            @RequestHeader(value = "Accept-Language",
                                                                    defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(reservationService.updateReservation(id, dto, appLanguage));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/pagination")
    @Operation(summary = "Api for Reservation get pagination", description = "by this api  to get pagination Reservation")
    public ResponseEntity<PageImpl<ReservationDto>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                               @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                               @RequestParam(value = "hotelId") Long hotelId) {
        return ResponseEntity.ok(reservationService.pagination(hotelId,page, size));
    }


}
