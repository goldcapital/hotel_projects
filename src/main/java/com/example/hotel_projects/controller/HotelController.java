package com.example.hotel_projects.controller;

import com.example.hotel_projects.dto.HotelDTO;
import com.example.hotel_projects.dto.RegionDto;
import com.example.hotel_projects.dto.request.HotelCreateRequest;
import com.example.hotel_projects.dto.request.HotelUpdateDto;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "Api for Hotel create", description = "by this api admin to create hotel")
    public ResponseEntity<HotelDTO> createHotel(@Valid @RequestBody HotelCreateRequest hotelDTO,
                                                @RequestHeader(value = "Accept-Language",
                                                        defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(hotelService.createHotel(hotelDTO, appLanguage));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{name}")
    @Operation(summary = "Api for Hotel delete", description = "by this api admin to delete hotel")
    public ResponseEntity<Boolean> delete(@PathVariable String name,
                                          @RequestHeader(value = "Accept-Language",
                                                  defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(hotelService.delete(name, appLanguage));

    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{name}")
    @Operation(summary = "Api for Hotel update", description = "by this api admin to update hotel")
    public ResponseEntity<HotelDTO> update(@PathVariable String name,
                                           @RequestBody HotelUpdateDto hotelDTO,
                                           @RequestHeader(value = "Accept-Language",
                                                   defaultValue = "uz") AppLanguage appLanguage) {

        return ResponseEntity.ok(hotelService.update(name, hotelDTO, appLanguage));
    }


    @GetMapping("/get_all")
    @Operation(summary = "Api for Hotel get all", description = "by this api  to get all hotel")
    public ResponseEntity<List<HotelDTO>> getAll() {
        return ResponseEntity.ok(hotelService.getAll());
    }

    @GetMapping("/get_by_name/{name}")
    @Operation(summary = "Api for Hotel get name", description = "by this api  to get name hotel")
    public ResponseEntity<HotelDTO> getByName(@PathVariable String name,
                                              @RequestHeader(value = "Accept-Language",
                                                      defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(hotelService.getByName(name, appLanguage));
    }

    @GetMapping("/pagination")
    @Operation(summary = "Api for Hotel get pagination", description = "by this api  to get pagination hotel")
    public ResponseEntity<PageImpl<HotelDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(hotelService.pagination(page, size));
    }

}
