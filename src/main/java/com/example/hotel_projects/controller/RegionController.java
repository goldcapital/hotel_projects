package com.example.hotel_projects.controller;

import com.example.hotel_projects.dto.RegionDto;
import com.example.hotel_projects.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/region")
public class RegionController {
    private final RegionService regionService;
    @PostMapping("/create/{name}")
    public ResponseEntity<RegionDto>createRegion(@PathVariable String name){
   return  ResponseEntity.ok(regionService.createRegion(name));
    }

}
