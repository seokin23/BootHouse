package likelion.eight.domain.review.infra;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.review.converter.ReviewConverter;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.port.ReviewRepository;
import likelion.eight.review.ReviewEntity;
import likelion.eight.review.ifs.ReviewJpaRepository;
import likelion.eight.user.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository
// 리뷰 리포지토리(인터페이스) 상속
public class ReviewRepositoryImpl implements ReviewRepository {

    private final ReviewJpaRepository reviewJpaRepository; //JPA 리포지토리 사용

    @Override
    public Review save(Review review, CourseEntity courseEntity, UserEntity userEntity) {
        ReviewEntity reviewEntity = ReviewConverter.toReviewEntity(review, courseEntity,userEntity);
        reviewEntity = reviewJpaRepository.save(reviewEntity);
        return ReviewConverter.toDto(reviewEntity);
    }

    @Override
    public Review save(ReviewEntity reviewEntity, CourseEntity courseEntity, UserEntity userEntity) {
        reviewJpaRepository.save(reviewEntity);
        return ReviewConverter.toDto(reviewEntity);
    }

    @Override
    public Review getById(Long id) {
        return reviewJpaRepository.findById(id)
                .map(ReviewConverter::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Review Not Found : " + id));
    }

    @Override
    public Optional<ReviewEntity> findById(Long id) {
        return reviewJpaRepository.findById(id);
    }

    @Override
    public List<Review> findByCourseId(Long courseId) {
        List<ReviewEntity> reviewEntities = reviewJpaRepository.findByCourseId(courseId);
        return reviewEntities.stream()
                .map(ReviewConverter::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<ReviewEntity> findAll(Pageable pageable) {
        return reviewJpaRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        reviewJpaRepository.deleteById(id);
    }

    @Override
    public boolean existsByUserIdAndCourseId(Long userId, Long courseId) {
        return reviewJpaRepository.existsByUserEntityIdAndCourseEntityId(userId, courseId);
    }

    @Override
    public Optional<Review> findReviewByCourseId(Long courseId) {
        return Optional.empty();
    }

    @Override
    public Optional<Review> findByCourseIdAndUserId(Long courseId, Long userId) {
        return reviewJpaRepository.findByCourseEntityIdAndUserEntityId(courseId, userId)
                .map(ReviewConverter::toDto);
    }

    @Override
    public Page<ReviewEntity> searchByKeyword(String keyword, Pageable pageable) {
        return reviewJpaRepository.searchByKeyword(keyword,pageable);
    }

    @Override
    public Page<ReviewEntity> sortByCondition(String sortBy, Pageable pageable) {

        return reviewJpaRepository.sortByCondition(sortBy, pageable);
    }

    @Override
    public Optional<ReviewEntity> findPreviousReview(Long reviewId) {
        return reviewJpaRepository.findTopByIdLessThanOrderByIdDesc(reviewId);
    }

    @Override
    public Optional<ReviewEntity> findNextReview(Long reviewId) {
        return reviewJpaRepository.findTopByIdGreaterThanOrderByIdAsc(reviewId);
    }
}
