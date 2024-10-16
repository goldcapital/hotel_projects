package com.example.hotel_projects.service;

import com.example.hotel_projects.dto.ReviewDto;
import com.example.hotel_projects.dto.request.ReviewRequestDto;
import com.example.hotel_projects.entity.ReviewEntity;
import com.example.hotel_projects.enums.AppLanguage;
import com.example.hotel_projects.exp.AppBadException;
import com.example.hotel_projects.repository.ReviewRepository;
import com.example.hotel_projects.utl.SpringSecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ResourceBundleService resourceBundleService;
    private final ReservationService reservationService;
    private final ProfileService profileService;

    public ReviewDto createReview(ReviewRequestDto dto, AppLanguage appLanguage) {

        if (reservationService.getByReservationId(dto.getReservationId(), appLanguage)) {
            if (profileService.getByIdAndIsLoggedIn(SpringSecurityUtil.getCurrentUser().getEmail(), appLanguage)) {
                reviewRepository.save(toEntity(dto));
                return toDto(toEntity(dto));
            }

        }
        return null;
    }

    private ReviewEntity toEntity(ReviewRequestDto dto) {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReservationId(dto.getReservationId());
        reviewEntity.setComment(dto.getComment());
        reviewEntity.setRating(dto.getRating());
        return reviewEntity;
    }

    private ReviewDto toDto(ReviewEntity entity) {

        ReviewDto reviewDto = new ReviewDto();

        reviewDto.setId(entity.getId());
        reviewDto.setComment(entity.getComment());
        reviewDto.setRating(entity.getRating());
        reviewDto.setReservationId(entity.getReservationId());
        reviewDto.setCreatedDate(entity.getCreationDate());
        return reviewDto;
    }

    public List<ReviewDto> getAll() {
        return  reviewRepository
                .findAll()
                .stream()
                .map(this::toDto)
                .toList();


    }

    public ReviewDto getById(Long id, AppLanguage appLanguage) {
        return reviewRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(()->
                        new AppBadException(resourceBundleService.getMessage("item.not.found",appLanguage)));

    }

    public Boolean delete(Long id, AppLanguage appLanguage) {
        if (profileService.getByIdAndIsLoggedIn(SpringSecurityUtil.getCurrentUser().getEmail(), appLanguage)){
            Optional<ReviewEntity>optional=reviewRepository.findById(id);

            if(optional.isPresent()){
                ReviewEntity reviewEntity=optional.get();
                reviewEntity.setVisible(false);
                reviewRepository.save(reviewEntity);
                return true;
            }
        }

        throw new AppBadException(resourceBundleService.getMessage("item.not.found",appLanguage));
    }
}