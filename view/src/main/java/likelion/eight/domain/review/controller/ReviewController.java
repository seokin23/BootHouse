package likelion.eight.domain.review.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewSearchCondition;
import likelion.eight.domain.review.controller.model.ReviewSortCondition;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

// 로그인 안한 사용자도 볼 수 있음

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;
    private final CourseService courseService;

    @GetMapping("/reviews")
    public String showAllReviews(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Review> reviewPage = reviewService.findAllReviews(pageable);

        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("searchCondition", new ReviewSearchCondition()); // 검색 조건 초기화
        model.addAttribute("sortCondition", new ReviewSortCondition()); // 정렬 조건 초기화

        return "review/showAllReviews";
    }

    @GetMapping("/reviews/search")
    public String searchReviews(
            Model model,
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {

        //검색 기능
        ReviewSearchCondition searchCondition = new ReviewSearchCondition(keyword);
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewService.searchReviews(searchCondition, pageable);

        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("searchCondition", searchCondition);
        model.addAttribute("sortCondition", new ReviewSortCondition()); // 정렬 조건 초기화

        return "review/showAllReviews";
    }

    @GetMapping("/reviews/sort")
    public String sortReviews(
            Model model,
            @RequestParam String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "2") int size
    ) {
        //정렬 기능 :  높은 평점순 / 낮은 평점순 / 조회수 / 최근순 / 오래된순
        ReviewSortCondition sortCondition = new ReviewSortCondition(sortBy);
        Pageable pageable = PageRequest.of(page, size);
        Page<Review> reviewPage = reviewService.sortReviews(sortCondition, pageable);

        model.addAttribute("reviewPage", reviewPage);
        model.addAttribute("sortCondition", sortCondition);
        model.addAttribute("searchCondition", new ReviewSearchCondition());

        return "review/showAllReviews";
    }

    @GetMapping("/reviews/{reviewId}")
    public String showReview(@PathVariable Long reviewId, Model model, @Login LoginUser loginUser) {

        reviewService.incrementViewCount(reviewId);

        Review review = reviewService.findReviewById(reviewId);
        Optional<Review> previousReviewOptional = reviewService.findPreviousReview(reviewId);
        Optional<Review> nextReviewOptional = reviewService.findNextReview(reviewId);

        Course course = courseService.findCourseById(review.getCourseId());

        model.addAttribute("course", course);
        model.addAttribute("review", review);
        previousReviewOptional.ifPresent(previousReview -> model.addAttribute("previousReview", previousReview));
        nextReviewOptional.ifPresent(nextReview -> model.addAttribute("nextReview", nextReview));
        model.addAttribute("loginUser", loginUser);
        return "review/showReview";
    }



}
