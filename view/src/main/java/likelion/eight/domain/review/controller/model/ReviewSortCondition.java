package likelion.eight.domain.review.controller.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewSortCondition {

    private String sortBy; //정렬 기준 (ratingDesc, ratingAsc, viewCount, recent, oldest)


    // 기본 생성자 추가
    public ReviewSortCondition() {
        this.sortBy = "";
    }

    // 파라미터를 받는 생성자 추가
    public ReviewSortCondition(String sortBy) {
        this.sortBy = sortBy;
    }

}
