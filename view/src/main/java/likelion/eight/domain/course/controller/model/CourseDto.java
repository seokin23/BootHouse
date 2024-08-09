package likelion.eight.domain.course.controller.model;

import likelion.eight.domain.course.model.Course;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CourseDto {
    private Long id;
    private String name;
    private String subCourseName;
    private String recruitmentStatusDescription;
    private LocalDateTime closingDate;
    private String tuitionType;
    private String onlineOffline;
    private String location;
    private LocalDate startDate;
    private LocalDate endDate;
    private String participationTimeDisplayName;
    private String participationTimeDefaultSchedule;
    private String selectionProcedure;

    public CourseDto(Course course){
        this.id = course.getId();
        this.name = course.getName();
        this.subCourseName = course.getSubCourse() != null ? course.getSubCourse().getName() : "프로그램 과정명 없음";
        this.recruitmentStatusDescription = course.getRecruitmentStatus().getDescription();
        this.closingDate = course.getClosingDate();
        this.tuitionType = course.getTuitionType();
        this.onlineOffline = course.isOnlineOffline() ? "온라인" : "오프라인";
        this.location = course.isOnlineOffline() ? "" : course.getLocation();
        this.startDate = course.getStartDate();
        this.endDate = course.getEndDate();
        this.participationTimeDisplayName = course.getParticipationTime().getDisplayName();
        this.participationTimeDefaultSchedule = course.getParticipationTime().getDefaultSchedule();
        this.selectionProcedure = course.isCodingTestExempt() ? "코딩 테스트 있음" : "코딩 테스트 없음";
    }
}
