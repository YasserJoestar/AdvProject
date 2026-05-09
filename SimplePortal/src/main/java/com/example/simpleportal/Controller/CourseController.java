package com.example.simpleportal.Controller;

import com.example.simpleportal.Model.Course;
import com.example.simpleportal.Service.ActivityLogService;
import com.example.simpleportal.Service.CourseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/")
public class CourseController {

    private final CourseService      service;
    private final ActivityLogService logService;

    public CourseController(CourseService service, ActivityLogService logService) {
        this.service    = service;
        this.logService = logService;
    }

    @GetMapping("/grades")
    public String showGrades(
            @CookieValue(name = "student_id",   required = false) String cookieId,
            @CookieValue(name = "student_role", required = false) String role,
            Model model) {
        if ("ADMIN".equalsIgnoreCase(role)) return "redirect:/admin/dashboard";
        Long sid = parseLong(cookieId);
        model.addAttribute("courses", service.getAllCourses(sid));
        model.addAttribute("course",  new Course());
        return "grades";
    }

    @PostMapping("/grades")
    public String addCourse(@ModelAttribute Course course,
                            @CookieValue(name = "student_id", required = false) String cookieId) {
        service.addCourse(course, parseLong(cookieId));
        return "redirect:/grades";
    }

    @GetMapping("/gpa-calculator")
    public String gpaCalculator(
            @CookieValue(name = "student_id",   required = false) String cookieId,
            @CookieValue(name = "student_role", required = false) String role,
            Model model) {
        if ("ADMIN".equalsIgnoreCase(role)) return "redirect:/admin/dashboard";
        Long sid = parseLong(cookieId);
        List<Course> courses = service.getAllCourses(sid);
        model.addAttribute("courses",    courses);
        model.addAttribute("course",     new Course());
        model.addAttribute("totalHours", service.calculateTotalHours(courses));
        model.addAttribute("gpa",        service.calculateGpa(courses));
        return "gpa-calculator";
    }

    @PostMapping("/gpa-calculator")
    public String addCourseForGpa(@ModelAttribute Course course,
                                  @CookieValue(name = "student_id", required = false) String cookieId) {
        service.addCourse(course, parseLong(cookieId));
        return "redirect:/gpa-calculator";
    }

    private Long parseLong(String raw) {
        if (raw == null || raw.isBlank()) return null;
        try { return Long.parseLong(raw.trim()); } catch (NumberFormatException e) { return null; }
    }
}
