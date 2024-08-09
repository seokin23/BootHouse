package likelion.eight.course.initializer;

import likelion.eight.SubCourseEntity.SubCourseEntity;
import likelion.eight.SubCourseEntity.ifs.SubCourseJpaRepository;
import likelion.eight.bootcamp.BootCampEntity;
import likelion.eight.bootcamp.ifs.BootCampJpaRepository;
import likelion.eight.category.CategoryEntity;
import likelion.eight.category.ifs.CategoryJpaRepository;
import likelion.eight.course.CourseEntity;
import likelion.eight.course.ParticipationTime;
import likelion.eight.course.ifs.CourseJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CourseInitData implements CommandLineRunner {
    @Autowired
    private CourseJpaRepository courseRepository;

    @Autowired
    private BootCampJpaRepository bootCampRepository;

    @Autowired
    private CategoryJpaRepository categoryRepository;

    @Autowired
    private SubCourseJpaRepository subCourseRepository;

    @Override
    public void run(String... args) throws Exception {
      
        // 멋쟁이사자처럼, 웹개발 카테고리 코스 여러개
//        BootCampEntity bootCamp = bootCampRepository.findByName("멋쟁이사자처럼");
//        CategoryEntity category = categoryRepository.findByName("디자인·3D");
//        SubCourseEntity subCourse = subCourseRepository.findByName("UXUI/디자인");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("UXUI 디자인 스쿨 3기")
//                .startDate(LocalDate.of(2024, 8, 12))
//                .endDate(LocalDate.of(2024, 12, 23))
//                .closingDate(LocalDateTime.of(2024, 8,5,23,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 풀타임으로 진행되는 KDT(무료) UI/UX디자인 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(60)
//                .build();
//
//        courseRepository.save(course);

//
//        BootCampEntity bootCamp = bootCampRepository.findByName("코드잇");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("프론트앤드");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("HTML/CSS/JS 기초 첫걸음")
//                .startDate(LocalDate.of(2024, 10, 5))
//                .endDate(LocalDate.of(2024, 12, 15))
//                .closingDate(LocalDateTime.of(2024, 10,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 파트타임으로 진행되는 KDC(무료) 프론트앤드 부트챌린지입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.PART_TIME)
//                .maxParticipants(50) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//        BootCampEntity bootCamp = bootCampRepository.findByName("멋쟁이사자처럼");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("퍼블리싱");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("웹퍼블리싱의 모든 것")
//                .startDate(LocalDate.of(2024, 11, 5))
//                .endDate(LocalDate.of(2024, 12, 29))
//                .closingDate(LocalDateTime.of(2024, 11,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 파트타임으로 진행되는 KDC(무료) 퍼블리싱 부트챌린지입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.PART_TIME)
//                .maxParticipants(30) // 정원없음
//                .build();
//
//        courseRepository.save(course);

//        BootCampEntity bootCamp = bootCampRepository.findByName("멋쟁이사자처럼");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("퍼블리싱");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("웹퍼블리싱 기초")
//                .startDate(LocalDate.of(2024, 10, 15))
//                .endDate(LocalDate.of(2025, 6, 19))
//                .closingDate(LocalDateTime.of(2024, 10,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("30")
//                .summary("온라인, 풀타임으로 진행되는 유료(30) 퍼블리싱 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(30) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//        BootCampEntity bootCamp = bootCampRepository.findByName("더조은컴퓨터아카데미");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("웹 풀스택");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("IT 부트캠프 프로젝트 기반 AWS 자바(스프링부트&리액트) 풀스택 개발자 양성 7회차")
//                .startDate(LocalDate.of(2024, 8, 19))
//                .endDate(LocalDate.of(2025, 3, 4))
//                .closingDate(LocalDateTime.of(2024, 8, 30, 0,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(false)
//                .location("서울") // -- 오프라인이라, 명시함
//                .tuitionType("무료")
//                .summary("서울에서 오프라인, 풀타임으로 진행되는 KDT(무료) 웹풀스택 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(20) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//         두번째 카테고리 :: 모바일
//        BootCampEntity bootCamp = bootCampRepository.findByName("코드잇");
//        CategoryEntity category = categoryRepository.findByName("모바일");
//        SubCourseEntity subCourse = subCourseRepository.findByName("iOS");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("생성형 AI활용 클라우드 기반 iOS 앱개발자 2024")
//                .startDate(LocalDate.of(2024, 8, 19))
//                .endDate(LocalDate.of(2024, 12, 11))
//                .closingDate(LocalDateTime.of(2024, 8, 18, 0,0))
//                .codingTestExempt(true)
//                .cardRequirement(false)
//                .onlineOffline(false)
//                .location("서울") // -- 오프라인이라, 명시함
//                .tuitionType("10") // -- 10만원
//                .summary("서울에서 오프라인, 풀타임으로 진행되는 유료(10만원) iOS 부트캠프입니다. 선발절차에 코딩테스트는 있습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(16) // 정원없음
//                .build();
//
//        courseRepository.save(course);
//
//
//         세번째 카테고리 :: 데이터 AI
//        BootCampEntity bootCamp = bootCampRepository.findByName("패스트캠퍼스");
//        CategoryEntity category = categoryRepository.findByName("데이터·AI");
//        SubCourseEntity subCourse = subCourseRepository.findByName("데이터분석");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("데이터분석 부트캠프 16기")
//
//                .startDate(LocalDate.of(2024, 9, 19))
//                .endDate(LocalDate.of(2025, 12, 10))
//                .closingDate(LocalDateTime.of(2024, 9, 4, 0,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location("서울") // -- 오프라인이라, 명시함
//                .tuitionType("무료") // -- 10만원
//                .summary("온라인, 풀타임으로 진행되는 KDT(무료) 데이터분석 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .maxParticipants(40) // 정원없음
//                .build();
//
//        courseRepository.save(course);

//        BootCampEntity bootCamp = bootCampRepository.findById(1L).get();
//        CategoryEntity category = categoryRepository.findById(7L).get();
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .name("UXUI 디자인 스쿨 3기")
//                .startDate(LocalDate.of(2024, 8, 12))
//                .endDate(LocalDate.of(2024, 12, 23))
//                .closingDate(LocalDateTime.of(2024, 8,12,23,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 풀타임으로 진행되는 KDT(무료) UI/UX디자인 부트캠프입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.FULL_TIME)
//                .averageRating(null)
//                .viewCounts(0) //-- 평점이랑 조회수는 우선 null, 0 초기값으로
//                .maxParticipants(60)
//                .build();
//
//
//        courseRepository.save(course);


//        BootCampEntity bootCamp = bootCampRepository.findByName("코드잇");
//        CategoryEntity category = categoryRepository.findByName("웹개발");
//        SubCourseEntity subCourse = subCourseRepository.findByName("프론트앤드");
//
//        CourseEntity course = CourseEntity.builder()
//                .bootcampEntity(bootCamp)
//                .categoryEntity(category)
//                .subCourseEntity(subCourse)
//                .name("HTML/CSS/JS 기초 첫걸음")
//                .startDate(LocalDate.of(2024, 10, 5))
//                .endDate(LocalDate.of(2024, 12, 15))
//                .closingDate(LocalDateTime.of(2024, 10,2,14,0))
//                .codingTestExempt(false)
//                .cardRequirement(true)
//                .onlineOffline(true)
//                //.location() -- 온라인이라 무시
//                .tuitionType("무료")
//                .summary("온라인, 파트타임으로 진행되는 KDC(무료) 프론트앤드 부트챌린지입니다. 선발절차에 코딩테스트는 없습니다.")
//                .participationTime(ParticipationTime.PART_TIME)
//                .maxParticipants(50) // 정원없음
//                .build();
//
//        courseRepository.save(course);

    }
}
