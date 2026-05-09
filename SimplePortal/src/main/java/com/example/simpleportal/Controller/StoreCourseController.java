package com.example.simpleportal.Controller;

import com.example.simpleportal.Service.StoreCourseRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.simpleportal.Model.StoreCourse;

@Controller
public class StoreCourseController {
    private final StoreCourseRepository storeCourseRepository;

    public StoreCourseController(StoreCourseRepository storeCourseRepository) {
        this.storeCourseRepository = storeCourseRepository;
    }

    @GetMapping("/Courses")
    public String courses(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("q", q == null ? "" : q);
        model.addAttribute("courses", storeCourseRepository.search(q));
        model.addAttribute("course", new StoreCourse());
        return "Courses";
    }

    @PostMapping("/Courses")
    public String addCourse(@ModelAttribute StoreCourse course) {
        storeCourseRepository.save(course);
        return "redirect:/Courses";
    }
}

