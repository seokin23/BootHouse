package likelion.eight.domain.review.converter;

import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.review.ReviewEntity;
import likelion.eight.user.UserEntity;

public class ReviewConverter {

    // 모델(DTO)을 -> 엔티티로 변경
    public static ReviewEntity toReviewEntity(Review review, CourseEntity courseEntity, UserEntity userEntity) {

        return ReviewEntity.builder()
                .id(review.getId())
                .courseEntity(courseEntity)
                .userEntity(userEntity)
                .title(review.getTitle())
                .oneLineReview(review.getOneLineReview())
                .advantages(review.getAdvantages())
                .disadvantages(review.getDisadvantages())
                .instructorEvaluation(review.getInstructorEvaluation())
                .rating(review.getRating())
                .viewCount(review.getViewCount() != null ? review.getViewCount() : 0)
                .registrationDate(review.getRegistrationDate())
                .lastModifiedDate(review.getLastModifiedDate())
                .build();
    }

    public static ReviewEntity toReviewEntity(ReviewCreateRequest reviewCreateRequest, CourseEntity courseEntity, UserEntity userEntity, ClockHolder clockHolder) {
        return ReviewEntity.builder()
                .id(reviewCreateRequest.getId())
                .courseEntity(courseEntity)
                .userEntity(userEntity)
                .title(reviewCreateRequest.getTitle())
                .oneLineReview(reviewCreateRequest.getOneLineReview())
                .advantages(reviewCreateRequest.getAdvantages())
                .disadvantages(reviewCreateRequest.getDisadvantages())
                .instructorEvaluation(reviewCreateRequest.getInstructorEvaluation())
                .rating(reviewCreateRequest.getRating())
                .viewCount(0)
                .registrationDate(clockHolder.now())
                .lastModifiedDate(clockHolder.now())
                .build();
    }


    // 엔티티를 모델(DTO)로 변경
    public static Review toDto(ReviewEntity reviewEntity) {
        return Review.builder()
                .id(reviewEntity.getId())
                .courseId(reviewEntity.getCourseEntity().getId())
                .userId(reviewEntity.getUserEntity().getId())
                .title((reviewEntity.getTitle()))
                .oneLineReview(reviewEntity.getOneLineReview())
                .advantages(reviewEntity.getAdvantages())
                .disadvantages(reviewEntity.getDisadvantages())
                .instructorEvaluation(reviewEntity.getInstructorEvaluation())
                .rating(reviewEntity.getRating())
                .viewCount(reviewEntity.getViewCount())
                .registrationDate(reviewEntity.getRegistrationDate())
                .lastModifiedDate(reviewEntity.getLastModifiedDate())
                .build();
    }

}
