package com.example.simpleportal.Service;

import com.example.simpleportal.Model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CourseService {

    private static final Long DEFAULT_STUDENT_ID = 1L;

    private final CatalogCourseRepository catalogCourseRepository;
    private final EnrollmentRepository    enrollmentRepository;
    private final StudentRepository       studentRepository;
    private final ActivityLogService      logService;

    public CourseService(CatalogCourseRepository catalogCourseRepository,
                         EnrollmentRepository enrollmentRepository,
                         StudentRepository studentRepository,
                         ActivityLogService logService) {
        this.catalogCourseRepository = catalogCourseRepository;
        this.enrollmentRepository    = enrollmentRepository;
        this.studentRepository       = studentRepository;
        this.logService              = logService;
    }
    public List<Course> getAllCourses(Long studentId) {
        long sid = studentId == null ? DEFAULT_STUDENT_ID : studentId;
        return enrollmentRepository
                .findByStudent_IdOrderBySemesterAscCatalogCourse_CourseCodeAsc(sid)
                .stream().map(this::toCourseDto).toList();
    }

    public List<Course> getAllCourses() { return getAllCourses(DEFAULT_STUDENT_ID); }

    public void addCourse(Course course, Long sessionStudentId) {
        if (course == null) return;

        Long studentId = sessionStudentId != null ? sessionStudentId
                : course.getStudentId() != null  ? course.getStudentId()
                : DEFAULT_STUDENT_ID;

        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) return;

        if (course.getCreditHours() == null || course.getCreditHours() < 1) course.setCreditHours(3);

        String sem  = (course.getSemester() == null || course.getSemester().isBlank()) ? "1" : course.getSemester().trim();
        String code = course.getCourseCode() == null ? "" : course.getCourseCode().trim();

        CatalogCourse cc = catalogCourseRepository.findByCourseCodeIgnoreCase(code)
                .orElseGet(() -> catalogCourseRepository.save(new CatalogCourse(
                        code.isBlank() ? "COURSE-" + System.currentTimeMillis() : code,
                        course.getCourseName() == null ? "" : course.getCourseName().trim(),
                        course.getInstructor() == null ? "" : course.getInstructor().trim(),
                        course.getCreditHours())));

        Enrollment enrollment = enrollmentRepository
                .findByStudent_IdAndCatalogCourse_IdAndSemester(studentId, cc.getId(), sem)
                .orElseGet(() -> new Enrollment(student, cc, sem, null));

        enrollment.setGrade(course.getGrade());
        enrollmentRepository.save(enrollment);

        // refresh student summary & log
        refreshStudentGpa(student, studentId);
        logService.log(student, "ADD_GRADE",
                "Enrolled in " + cc.getCourseName() + " (Sem " + sem + ")"
                + (course.getGrade() != null ? " grade=" + course.getGrade() : ""));
    }

    public void addCourse(Course course) { addCourse(course, null); }

    public double calculateGpa(List<Course> courses) {
        double pts = 0; int hrs = 0;
        for (Course c : courses) {
            int h = c.getCreditHours() == null ? 0 : c.getCreditHours();
            if (h <= 0) continue;
            pts += gradeToPoints(c.getGrade()) * h;
            hrs += h;
        }
        return hrs == 0 ? 0.0 : pts / hrs;
    }

    public int calculateTotalHours(List<Course> courses) {
        return courses.stream().map(Course::getCreditHours)
                .filter(h -> h != null && h > 0).reduce(0, Integer::sum);
    }

    private void refreshStudentGpa(Student student, Long studentId) {
        List<Course> courses = getAllCourses(studentId);
        student.setCurrentGpa((float) calculateGpa(courses));
        student.setCourseCount(courses.size());
        studentRepository.save(student);
    }

    private double gradeToPoints(String grade) {
        if (grade == null) return 0.0;
        return switch (grade.trim().toUpperCase(Locale.ROOT)) {
            case "A+", "A" -> 4.0;
            case "A-"      -> 3.7;
            case "B+"      -> 3.3;
            case "B"       -> 3.0;
            case "B-"      -> 2.7;
            case "C+"      -> 2.3;
            case "C"       -> 2.0;
            case "C-"      -> 1.7;
            case "D+"      -> 1.3;
            case "D"       -> 1.0;
            default        -> 0.0;
        };
    }

    private Course toCourseDto(Enrollment e) {
        Course dto = new Course();
        dto.setId(e.getId());
        dto.setStudentId(e.getStudent() == null ? null : e.getStudent().getId());
        dto.setSemester(e.getSemester());
        dto.setGrade(e.getGrade());
        CatalogCourse cc = e.getCatalogCourse();
        if (cc != null) {
            dto.setCourseCode(cc.getCourseCode());
            dto.setCourseName(cc.getCourseName());
            dto.setInstructor(cc.getInstructor());
            dto.setCreditHours(cc.getCreditHours());
        }
        return dto;
    }
}
