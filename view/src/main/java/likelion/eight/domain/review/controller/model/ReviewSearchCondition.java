package likelion.eight.domain.review.controller.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
public class ReviewSearchCondition {

    private String keyword;


    // 기본 생성자 추가
    public ReviewSearchCondition() {
        this.keyword = "";
    }

    // 파라미터를 받는 생성자 추가
    public ReviewSearchCondition(String keyword) {
        this.keyword = keyword;
    }

}
