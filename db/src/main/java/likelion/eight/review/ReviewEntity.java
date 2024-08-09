package likelion.eight.review;

import jakarta.persistence.*;
import likelion.eight.BaseTimeEntity;
import likelion.eight.course.CourseEntity;
import likelion.eight.user.UserEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "reviews")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
// BaseTimeEntity 등록일, 수정일 정보를 담고 있음
public class ReviewEntity extends BaseTimeEntity {

    @Id
    @Column(name = "review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 리뷰 아이디

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity courseEntity;  // 코스를 참조하고 있음

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;  // 유저를 참조하고 있음.

    @Column(nullable = false, length = 50)
    private String title; //제목

    @Column(nullable = false,length = 50)
    private String oneLineReview; //한 줄 리뷰

    @Column(nullable = false)
    private String advantages; //장점

    @Column(nullable = false)
    private String disadvantages; //단점

    @Column(nullable = false)
    private String instructorEvaluation; //강사 평가

    @Column(nullable = false)
    private Integer rating; //평점

    private Integer viewCount; //조회수 카운팅

    // view count ++
    public void incrementViewCount() {
        this.viewCount++;
    }

}