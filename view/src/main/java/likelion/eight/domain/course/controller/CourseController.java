package likelion.eight.domain.course.controller;

import jakarta.servlet.http.HttpServletRequest;
import likelion.eight.common.annotation.Login;
import likelion.eight.common.service.CookieService;
import likelion.eight.course.ifs.CourseJpaRepository;
import likelion.eight.domain.category.model.Category;
import likelion.eight.domain.category.service.CategoryService;
import likelion.eight.domain.course.controller.model.CourseDetailDto;
import likelion.eight.domain.course.controller.model.CourseDto;
import likelion.eight.domain.course.controller.model.CourseFilter;
import likelion.eight.domain.course.controller.model.ReviewDto;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.port.ReviewRepository;
import likelion.eight.domain.subcourse.model.SubCourse;
import likelion.eight.domain.subcourse.service.SubCourseService;
import likelion.eight.domain.token.service.TokenService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CourseController {
    private final CourseService courseService;
    private final CategoryService categoryService;
    private final SubCourseService subCourseService;
    private final ReviewRepository reviewRepository;
    private final CookieService cookieService;

    // 모집중인 캠프 조회
    @GetMapping("/boothouse/camps")
    public String getOpenCourses(
            Model model,
            @RequestParam(required = false, name = "categories") Long categoryId,
            @ModelAttribute CourseFilter courseFilter,
            @RequestParam(required = false, name = "sort") String sort,
            @RequestParam(required = false, name = "search") String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        List<Category> categories = categoryService.findAll();

        Pageable pageable = PageRequest.of(page, size);

        // 모집중인 코스를 기준으로 필터링 (모집중 고려 O, 카테고리 고려 전, 필터링 기준 고려 O)
        Page<Course> coursePage = courseService.findCoursesByFilters(categoryId, courseFilter, sort, search, pageable);

        // 도메인을 dto로 변환
        List<CourseDto> courseDtos = coursePage.getContent().stream()
                .map(course -> new CourseDto(course))
                .collect(Collectors.toList());

        // 카테고리 조건 추가 (모집중 고려 O, 카테고리 고려 O, 필터링 기준 고려 O)
        if (categoryId != null){
            Category category = categoryService.findById(categoryId);
            List<SubCourse> subCourseList = subCourseService.findSubCourseByCategoryId(categoryId);

            // 선택된 Category와 그에 맞는 SubCourse 모델에 추가
            model.addAttribute("selectedCategory", category);
            model.addAttribute("subCourseList", subCourseList);
        }

        model.addAttribute("courses", courseDtos);
        model.addAttribute("count", coursePage.getTotalElements()); // 총 코스 개수
        model.addAttribute("categories", categories);

        model.addAttribute("totalPages", coursePage.getTotalPages()); // 총 페이지수
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("size", size); // 페이지 사이즈

        return "course/courseList";
    }

    // course 자세히보기
    @GetMapping("/courses/{id}")
    public String getCourseDetail(@PathVariable(name = "id")Long courseId,
                                  Model model,
                                  HttpServletRequest request){
        Course course = courseService.findCourseById(courseId);
        List<Review> reviews = reviewRepository.findByCourseId(courseId);
        boolean isUserLoggedIn = cookieService.isUserLoggedIn(request);

        course.calculateAverageRating(reviews);

        // Review 도메인을 dto로
        List<ReviewDto> reviewDtos = reviews.stream()
                .map(review -> new ReviewDto(review))
                .collect(Collectors.toList());

        // Course 도메인을 dto로
        CourseDetailDto courseDto = CourseDetailDto.from(course);

        model.addAttribute("course", courseDto);
        model.addAttribute("reviews", reviewDtos);
        model.addAttribute("isUserLoggedIn", isUserLoggedIn);

        return "course/courseDetail";
    }
}