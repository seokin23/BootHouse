package likelion.eight.domain.course.infra;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import likelion.eight.course.CourseEntity;
import likelion.eight.course.ParticipationTime;
import likelion.eight.domain.course.controller.model.CourseFilter;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourseSpecification {
    public static Specification<CourseEntity> filterCourses(
            Long categoryId,
            CourseFilter courseFilter,
            String sort,
            String search
    ) {
        return (root, query, criteriaBuilder) -> {

            // query.getResultType()을 통해 현재 쿼리가 count 쿼리인지 확인
            if (CourseEntity.class.equals(query.getResultType())) {
                // count 쿼리가 아닐 때만 fetch join 적용
                root.fetch("bootcampEntity", JoinType.LEFT);
                root.fetch("categoryEntity", JoinType.LEFT);
                root.fetch("subCourseEntity", JoinType.LEFT);
                query.distinct(true);
            }

            List<Predicate> predicates = new ArrayList<>();

            // 모집중 필터
            predicates.add(criteriaBuilder.greaterThan(root.get("closingDate"), LocalDateTime.now()));

            // 카테고리 필터
            if (categoryId != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoryEntity").get("id"), categoryId));
            }

            // 온라인/오프라인 필터
            String onlineOffline = courseFilter.getOnlineOffline();
            if (onlineOffline != null) {
                boolean isOnline = "online".equalsIgnoreCase(onlineOffline);
                predicates.add(criteriaBuilder.equal(root.get("onlineOffline"), isOnline));
            }

            // 지역 필터
            String location = mapLocation(courseFilter.getLocation());
            if (location != null) {
                predicates.add(criteriaBuilder.equal(root.get("location"), location));
            }

            // 비용 필터
            String cost = courseFilter.getCost();
            if (cost != null) {
                if ("free".equalsIgnoreCase(cost)) {
                    predicates.add(criteriaBuilder.equal(root.get("tuitionType"), "무료"));
                } else if ("paid".equalsIgnoreCase(cost)) {
                    predicates.add(criteriaBuilder.notEqual(root.get("tuitionType"), "무료"));
                }
            }

            // 참여 시간 필터
            String participationTime = courseFilter.getParticipationTime();
            if (participationTime != null) {
                boolean isFullTime = "full-time".equalsIgnoreCase(participationTime);
                predicates.add(criteriaBuilder.equal(root.get("participationTime"),
                        isFullTime ? ParticipationTime.FULL_TIME.getDatabaseName() : ParticipationTime.PART_TIME.getDatabaseName()));
            }

            // 선발 절차 필터
            String selectionProcedure = courseFilter.getSelectionProcedure();
            if (selectionProcedure != null) {
                boolean isCodingTestExempt = true;
                if ("easy".equalsIgnoreCase(selectionProcedure)) {
                    isCodingTestExempt = false;
                }
                predicates.add(criteriaBuilder.equal(root.get("codingTestExempt"), isCodingTestExempt));
            }

            // subCourse 필터
            Long subCourseId = courseFilter.getSubCourse();
            if (subCourseId != null) {
                predicates.add(criteriaBuilder.equal(root.get("subCourseEntity").get("id"), subCourseId));
            }

            // 검색어 필터
            if (search != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + search + "%"));
            }

            // 정렬 조건 추가
            if (sort != null) {
                switch (sort) {
                    case "deadline":
                        query.orderBy(criteriaBuilder.asc(root.get("closingDate")));
                        break;
                    case "startDate":
                        query.orderBy(criteriaBuilder.asc(root.get("startDate")));
                        break;
                    case "lowCost":
                        query.orderBy(criteriaBuilder.asc(root.get("tuitionType")));
                        break;
                    case "highCost":
                        query.orderBy(criteriaBuilder.desc(root.get("tuitionType")));
                        break;
                    case "shortDuration":
                        query.orderBy(criteriaBuilder.asc(criteriaBuilder.diff(root.get("endDate"), root.get("startDate"))));
                        break;
                    case "longDuration":
                        query.orderBy(criteriaBuilder.desc(criteriaBuilder.diff(root.get("endDate"), root.get("startDate"))));
                        break;
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    // html 값 - DB 값 매핑함수
    private static String mapLocation(String location) {
        if (location != null) {
            switch (location) {
                case "seoul":
                    return "서울";
                case "busan":
                    return "부산";
                default:
                    return null;
            }
        }
        return null;
    }
}
