package com.example.hotel_projects.controller;

import com.example.hotel_projects.dto.ReviewDto;
import com.example.hotel_projects.dto.request.ReviewRequestDto;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/create")
    public ResponseEntity<ReviewDto>createReview(@RequestBody ReviewRequestDto dto,
                                                 @RequestHeader(value = "Accept-Language",
                                                         defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(reviewService.createReview(dto,appLanguage));
    }
    @GetMapping("/get-all")
    public ResponseEntity<List<ReviewDto>>getAll(){
        return ResponseEntity.ok(reviewService.getAll());
    }
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ReviewDto>getById(@PathVariable Long id,
                                            @RequestHeader(value = "Accept-Language",
                                                    defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(reviewService.getById(id,appLanguage));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean>delete(@PathVariable Long id,
                                         @RequestHeader(value = "Accept-Language",
                                                 defaultValue = "uz") AppLanguage appLanguage){
        return ResponseEntity.ok(reviewService.delete(id,appLanguage));
    }

}
