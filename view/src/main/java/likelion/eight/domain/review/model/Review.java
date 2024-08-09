package likelion.eight.domain.review.model;

import likelion.eight.common.service.port.ClockHolder;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
public class Review {

    private final ClockHolder clockHolder;

    private Long id;
    private Long userId;
    private Long courseId;
    private String title;
    private String oneLineReview;
    private String advantages;
    private String disadvantages;
    private String instructorEvaluation;
    private Integer rating;

    private Integer viewCount;
    private LocalDateTime registrationDate;
    private LocalDateTime lastModifiedDate;



    public void update(ReviewUpdateRequest reviewUpdateRequest, ClockHolder clockHolder) {
        if (reviewUpdateRequest.getTitle() != null) {
            this.title = reviewUpdateRequest.getTitle();
        }
        if (reviewUpdateRequest.getOneLineReview() != null) {
            this.oneLineReview = reviewUpdateRequest.getOneLineReview();
        }
        if (reviewUpdateRequest.getAdvantages() != null) {
            this.advantages = reviewUpdateRequest.getAdvantages();
        }
        if (reviewUpdateRequest.getDisadvantages() != null) {
            this.disadvantages = reviewUpdateRequest.getDisadvantages();
        }
        if (reviewUpdateRequest.getInstructorEvaluation() != null) {
            this.instructorEvaluation = reviewUpdateRequest.getInstructorEvaluation();
        }
        if (reviewUpdateRequest.getRating() != null) {
            this.rating = reviewUpdateRequest.getRating();
        }
        this.lastModifiedDate = clockHolder.now();

    }
}
