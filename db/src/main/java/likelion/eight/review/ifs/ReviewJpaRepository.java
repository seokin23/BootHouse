package likelion.eight.review.ifs;

import likelion.eight.review.ReviewEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface ReviewJpaRepository extends JpaRepository<ReviewEntity, Long> {

    boolean existsByUserEntityIdAndCourseEntityId(Long userId, Long courseId);

    Optional<ReviewEntity> findByCourseEntityIdAndUserEntityId(Long courseId, Long userId);

    @Query("select r from ReviewEntity r where r.courseEntity.id = :courseId")
    List<ReviewEntity> findByCourseId(Long courseId);

    List<ReviewEntity> findByTitleContainingAndOneLineReviewContaining(String title, String oneLineReview);
    Page<ReviewEntity> findAll(Pageable pageable);

    @Query("SELECT r FROM ReviewEntity r WHERE " +
            "LOWER(r.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.oneLineReview) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.advantages) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.disadvantages) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(r.instructorEvaluation) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ReviewEntity> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);



    @Query("SELECT r FROM ReviewEntity r " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'ratingDesc' THEN r.rating END DESC, " +
            "CASE WHEN :sortBy = 'ratingAsc' THEN r.rating END ASC, " +
            "CASE WHEN :sortBy = 'viewCount' THEN r.viewCount END DESC, " +
            "CASE WHEN :sortBy = 'recent' THEN r.registrationDate END DESC, " +
            "CASE WHEN :sortBy = 'oldest' THEN r.registrationDate END ASC")
    Page<ReviewEntity> sortByCondition(String sortBy, Pageable pageable);


    Optional<ReviewEntity> findTopByIdLessThanOrderByIdDesc(Long id);
    Optional<ReviewEntity> findTopByIdGreaterThanOrderByIdAsc(Long id);


}
