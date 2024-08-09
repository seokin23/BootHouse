package likelion.eight.domain.course.service.port;

import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface CourseRepository {
    List<Course> findByClosingDateAfter(LocalDateTime now);

    List<Course> findByOpenCoursesByCategory(Long categoryId);
    Page<Course> findCoursesByFilters(
            Long categoryId,
            CourseFilter courseFilter,
            String sort,
            String search,
            Pageable pageable
    );

    Optional<Course> findCourseById(Long courseId);

    Optional<CourseEntity> findByCourseId(Long courseId);
}