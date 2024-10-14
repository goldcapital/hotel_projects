package com.example.hotel_projects.controller;

import com.example.hotel_projects.dto.HotelDTO;
import com.example.hotel_projects.dto.RoomDTO;
import com.example.hotel_projects.dto.request.RoomRequestDto;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.service.RoomService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    @Operation(summary = "Api for Room create", description = "by this api admin to create Room")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomRequestDto dto,
                                              @RequestHeader(value = "Accept-Language",
                                                      defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(roomService.createRoom(dto, appLanguage));

    }
    @GetMapping("/getAll")
    @Operation(summary = "Api for Hotel get all", description = "by this api  to get all hotel")
    public ResponseEntity<List<RoomDTO>> getAllRoom() {
        return ResponseEntity.ok(roomService.getRoomAll());
    }

    @GetMapping("/get_room/{number}")
    @Operation(summary = "Api for Hotel get number", description = "by this api  to get number hotel")
    public ResponseEntity<RoomDTO> getByNumber(@PathVariable String number,
                                               @RequestHeader(value = "Accept-Language",
                                                       defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(roomService.getRoomByNumber(number, appLanguage));

    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete")
    @Operation(summary = "Api for Hotel delete", description = "by this api admin to delete hotel")
    public ResponseEntity<Boolean> deleteRoom(@RequestParam("number") String roomNumber,
                                              @RequestParam("hotelId") Long hotelId,
                                              @RequestHeader(value = "Accept-Language",
                                                      defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(roomService.deleteRoom(roomNumber, hotelId, appLanguage));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update")
    @Operation(summary = "Api for Hotel update", description = "by this api admin to update hotel")
    public ResponseEntity<Boolean> updateRoom(@RequestParam("number") String roomNumber,
                                              @RequestParam("hotelId") Long hotelId,
                                              @RequestBody RoomDTO roomDTO,
                                              @RequestHeader(value = "Accept-Language",
                                                      defaultValue = "uz") AppLanguage appLanguage) {
        return ResponseEntity.ok(roomService.updateRoom(roomNumber, roomDTO, hotelId, appLanguage));
    }

    @GetMapping("/pagination")
    @Operation(summary = "Api for Hotel get pagination", description = "by this api  to get pagination hotel")
    public ResponseEntity<PageImpl<RoomDTO>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(roomService.pagination(page, size));
    }
}
