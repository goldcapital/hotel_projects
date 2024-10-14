package com.example.hotel_projects.controller;

import com.example.hotel_projects.dto.HotelDTO;
import com.example.hotel_projects.dto.RegionDto;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.service.RegionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create/{name}")
    @Operation(summary = "Api for Region create", description = "by this api admin to create Region")
    public ResponseEntity<RegionDto>createRegion(@PathVariable String name,
                                                 @RequestHeader(value = "Accept-Language",
                                                         defaultValue = "uz") AppLanguage appLanguage){
   return  ResponseEntity.ok(regionService.createRegion(name,appLanguage));
    }

    @GetMapping("/get_by_id/{id}")
    @Operation(summary = "Api for Region get By ID", description = "Get region by admin Id")
    public ResponseEntity<RegionDto>getById(@PathVariable Long id,
                                                 @RequestHeader(value = "Accept-Language",
                                                   defaultValue = "uz") AppLanguage appLanguage){
        return  ResponseEntity.ok(regionService.getById(id,appLanguage));

    }
    @GetMapping("/get_all")
    @Operation(summary = "Api for Region get All", description = "region by admin Get all")
    public  ResponseEntity<List<RegionDto>>getAll(){
        return ResponseEntity.ok(regionService.getAll());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Api for Region delete By ID", description = "delete region by admin Id")
    public ResponseEntity<RegionDto>delete(@PathVariable Long id,
                                           @RequestHeader(value = "Accept-Language",
                                                   defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(regionService.delete(id,appLanguage));

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/update")
    @Operation(summary = "Api for Region update By ID", description = "Update region by admin Id")
    public ResponseEntity<Boolean>update(@RequestBody RegionDto dto,
                                         @RequestHeader(value = "Accept-Language",
                                                 defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(regionService.update(dto,appLanguage));
    }
    @GetMapping("/pagination")
    @Operation(summary = "Api for Region get pagination", description = "by this api  to get pagination Region")
    public ResponseEntity<PageImpl<RegionDto>> pagination(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                         @RequestParam(value = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(regionService.pagination(page, size));
    }
}
