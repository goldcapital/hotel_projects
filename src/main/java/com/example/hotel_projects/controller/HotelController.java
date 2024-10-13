package com.example.hotel_projects.controller;

import com.example.hotel_projects.dto.HotelDTO;
import com.example.hotel_projects.dto.request.HotelCreateRequest;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotel")
@RequiredArgsConstructor
public class HotelController {
    private final  HotelService hotelService;
//admen
    @PostMapping("/create")
    @Operation(summary = "Api for Hotel create", description = "by this api admin to create hotel")
    public ResponseEntity<HotelDTO>createHotel(@Valid  @RequestBody HotelCreateRequest hotelDTO ,
                                               @RequestHeader(value = "Accept-Language",
                                                       defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(hotelService.createHotel(hotelDTO,appLanguage));
    }
    //admin
    @DeleteMapping("/delete/{name}")
    @Operation(summary = "Api for Hotel delete", description = "by this api admin to delete hotel")
    public ResponseEntity<Boolean>delete(@PathVariable String name,
                                         @RequestHeader(value = "Accept-Language",
                                                 defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(hotelService.delete(name,appLanguage));

    }
    //admin
    @PutMapping("/update/{name}")
    @Operation(summary = "Api for Hotel update", description = "by this api admin to update hotel")
    public ResponseEntity<HotelDTO>update(@PathVariable String name,
                                          @RequestBody HotelCreateRequest hotelDTO ,
                                          @RequestHeader(value = "Accept-Language",
                                          defaultValue = "uz") AppLanguage appLanguage){

        return ResponseEntity.ok(hotelService.update(name,hotelDTO,appLanguage));
    }
    //admin
    @GetMapping("/get_all")
    @Operation(summary = "Api for Hotel get all", description = "by this api admin to get all hotel")
    public ResponseEntity<List<HotelDTO>>getAll(){
        return ResponseEntity.ok(hotelService.getAll());
    }
    @GetMapping("/get_by_name/{name}")
    public ResponseEntity<HotelDTO>getByName(@PathVariable String name,
                                             @RequestHeader(value = "Accept-Language",
                                                     defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(hotelService.getByName(name,appLanguage));
    }
}
