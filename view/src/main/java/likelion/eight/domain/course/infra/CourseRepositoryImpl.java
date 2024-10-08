package likelion.eight.domain.course.infra;

import likelion.eight.common.domain.exception.ResourceNotFoundException;
import likelion.eight.course.CourseEntity;
import likelion.eight.course.ifs.CourseJpaRepository;
import likelion.eight.domain.course.controller.model.CourseDto;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.converter.CourseConverter;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.port.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Repository
@RequiredArgsConstructor
public class CourseRepositoryImpl implements CourseRepository {
    private final CourseJpaRepository courseJpaRepository;
    private final CourseQueryDSLRepositoryImpl courseQueryDSLRepository;

    // 모집중인 코스만 반환
    @Override
    public List<Course> findByClosingDateAfter(LocalDateTime now) {
        List<CourseEntity> courseEntityList = courseJpaRepository.findByClosingDateAfter(now);
        return CourseConverter.toCourseList(courseEntityList);
    }

    // 카테고리별, 모집중인 코스 반환

    @Override
    public List<Course> findByOpenCoursesByCategory(Long categoryId) {
        List<CourseEntity> courseEntityList = courseJpaRepository.findByOpenCoursesByCategory(categoryId);
        return CourseConverter.toCourseList(courseEntityList);
    }

    // 필터조건에 맞는 코스 리스트 반환
    @Override
    public Page<Course> findCoursesByFilters(Long categoryId, CourseFilter courseFilter,
                                             String sort, String search,
                                             Pageable pageable) {
        Specification<CourseEntity> specification = CourseSpecification.filterCourses(categoryId, courseFilter, sort, search);

        // Fetch join을 포함한 실제 쿼리
        Page<CourseEntity> courseEntityPage = courseJpaRepository.findAll(specification, pageable);

        return courseEntityPage.map(CourseConverter::toCourse);
    }

    @Override
    public Optional<CourseEntity> findByCourseId(Long courseId) {
        return courseJpaRepository.findById(courseId);
    }


    @Override
    public Optional<Course> findCourseById(Long courseId) {
        // fetch join을 통해 필요한 엔디티만 로딩
        Optional<CourseEntity> courseEntityOptional = courseJpaRepository.findByIdWithBootcamp(courseId);

        return CourseConverter.toCourseOptional(courseEntityOptional);
    }
    @Override
    public List<Course> findCourseByBootcampId(Long bootcampId) {
        List<CourseEntity> courseEntities = courseJpaRepository.findCoursesByBootcampId(bootcampId);

        return courseEntities.stream()
                .map(CourseConverter::toCourse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Course> getAllCourse() {
        List<CourseEntity> AllCourse = courseJpaRepository.findAll();

        return AllCourse.stream()
                .map(CourseConverter::toCourse)
                .collect(Collectors.toList());
      
    // QueryDSL로 필요한 칼럼 개수의 Course만 반환
    @Override
    public Page<CourseDto> findCoursesByFiltersQueryDSL(Long categoryId,
                                                        CourseFilter courseFilter,
                                                        String sort,
                                                        String search,
                                                        Pageable pageable) {
        return courseQueryDSLRepository.findCoursesByFiltersQueryDSL(categoryId, courseFilter, sort, search, pageable);
    }
}