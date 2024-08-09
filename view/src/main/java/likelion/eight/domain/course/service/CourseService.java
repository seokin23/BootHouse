package likelion.eight.domain.course.service;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.course.CourseEntity;
import likelion.eight.domain.course.controller.CourseController;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseRepository;
import likelion.eight.domain.user.controller.model.LoginUser;
import likelion.eight.domain.user.converter.UserConverter;
import likelion.eight.domain.user.model.User;
import likelion.eight.domain.user.service.port.UserRepository;
import likelion.eight.likeCourse.LikeCourseEntity;
import likelion.eight.likeCourse.LikeCourseJpaRepository;
import likelion.eight.user.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;
    private final LikeCourseJpaRepository likeCourseJpaRepository;
    private final UserRepository userRepository;

    public List<Course> getOpenCourses(){
        LocalDateTime now = LocalDateTime.now();
        List<Course> courses = courseRepository.findByClosingDateAfter(now);
        return Optional.ofNullable(courses)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No open courses found."));
    }

    public List<Course> findByOpenCoursesByCategory(Long categoryId){
        List<Course> courses = courseRepository.findByOpenCoursesByCategory(categoryId);
        return Optional.ofNullable(courses)
                .filter(c -> !c.isEmpty())
                .orElseThrow(() -> new ResourceNotFoundException("No open courses found for category ID: " + categoryId));
    }

    public Page<Course> findCoursesByFilters(
            Long categoryId,
            CourseFilter courseFilter,
            String sort,
            String search,
            Pageable pageable
    ){
        return courseRepository.findCoursesByFilters(categoryId, courseFilter, sort, search, pageable);
    }

    public Course findCourseById(Long courseId){
        // fetch join을 통해 필요한 엔디티만 로딩
        return courseRepository.findCourseById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("course", courseId));
    }

    public boolean isCourseLikedByUser(Long userId, Long courseId){
        return likeCourseJpaRepository.findByUserIdAndCourseId(userId, courseId).isPresent();
    }

    public String toggleLikeCourse(Long courseId, Long userId){
        Optional<LikeCourseEntity> existingLikeCourse = likeCourseJpaRepository.findByUserIdAndCourseId(userId, courseId);

        UserEntity userEntity = userRepository.findById(userId)
                .map(UserConverter::toEntity)
                .orElseThrow(() -> new ResourceNotFoundException("user", userId));

        CourseEntity courseEntity = courseRepository.findByCourseId(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("course", courseId));

        if (existingLikeCourse.isPresent()){
            likeCourseJpaRepository.delete(existingLikeCourse.get());
            return "찜하기를 취소했습니다.";
        } else {
            LikeCourseEntity newLikeCourseEntity = LikeCourseEntity.createLikeCourseEntity(userEntity, courseEntity);
            likeCourseJpaRepository.save(newLikeCourseEntity);
            return "찜하기를 추가했습니다.";
        }
    }
}
