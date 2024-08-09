package likelion.eight.domain.review.controller;

import likelion.eight.common.annotation.Login;
import likelion.eight.domain.course.model.Course;
import likelion.eight.domain.course.service.CourseService;
import likelion.eight.domain.review.controller.model.ReviewCreateRequest;
import likelion.eight.domain.review.controller.model.ReviewUpdateRequest;
import likelion.eight.domain.review.model.Review;
import likelion.eight.domain.review.service.ReviewService;
import likelion.eight.domain.user.controller.model.LoginUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// 로그인 사용자만 볼 수 있는 controller

@Controller
@RequiredArgsConstructor
@Slf4j
public class ReviewWithLoginController {

    private final ReviewService reviewService;
    private final CourseService courseService;

    @GetMapping("/reviews/new/{courseId}")
    public String createReviewForm(Model model, @PathVariable Long courseId, @Login LoginUser loginUser, RedirectAttributes redirectAttributes) {

        Long userId = loginUser.getId();
        if (reviewService.existsByUserIdAndCourseId(userId, courseId)) {

            model.addAttribute("error", "이미 해당 코스에 대한 리뷰를 작성하였습니다.");

            Long reviewId = reviewService.findReviewByCourseIdAndUserId(courseId, userId).getId();
            redirectAttributes.addAttribute("reviewId", reviewId);

            log.info("reviewID ::::: {}", reviewId);

            return "redirect:/reviews/{reviewId}";
        }

        Course course = courseService.findCourseById(courseId);

        model.addAttribute("course", course);
        model.addAttribute("loginUser", loginUser);
        model.addAttribute("reviewCreateRequest", ReviewCreateRequest.builder().build());

        return "review/reviewRegisterForm";
    }

    @PostMapping("/reviews/new/{courseId}")
    public String createReview(@ModelAttribute ReviewCreateRequest reviewCreateRequest, @PathVariable Long courseId, @Login LoginUser loginUser) {

        Long userId = loginUser.getId();

        reviewService.saveReview(reviewCreateRequest, userId, courseId);

        return "redirect:/reviews";
    }


    @GetMapping("review/{id}/edit")
    public String editReviewForm(@PathVariable Long id, Model model) {

        //TODO : 로그인 유저 = review의 user_id, 일치하면 수정,삭제 가능하도록 파라미터 넘겨 주기

        Review review = reviewService.findReviewById(id);
        model.addAttribute("review", review);
        return "review/editReviewForm";
    }

    @PostMapping("review/{id}/edit")
    public String editReview(@PathVariable Long id, @ModelAttribute ReviewUpdateRequest reviewUpdateRequest) {

        reviewService.updateReview(id, reviewUpdateRequest);
        return "redirect:/reviews";
    }

    @PostMapping("review/{id}/delete")
    public String deleteReview(@PathVariable Long id) {
        //TODO : 로그인 유저 = review의 user_id, 일치하면 수정,삭제 가능하도록 파라미터 넘겨 주기

        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}
