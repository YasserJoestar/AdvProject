package com.example.simpleportal.Service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
public class StudentService {
    public Map<String, Object> processStudentData(String name, int semester, String faculty) {
        Map<String, Object> response = new HashMap<>();
        List<String> courses = List.of();

        String facultyKey = faculty == null ? "" : faculty.toLowerCase(Locale.ROOT);

        if (facultyKey.equals("engineering")) {
            switch (semester) {
                case 1 : courses = Arrays.asList("Introduction to Computing", "Engineering Drawing", "Mathematics 1", "Physics 1", "Mechanics");
                break;
                case 2 : courses = Arrays.asList("Math 2", "Physics 2", "Mechanics 2", "Engineering Drawing", "Structured Programming");
                break;
                case 3 : courses = Arrays.asList("Math 3", "Data Structures", "Logic Design", "Electronics 1", "Thermodynamics");
                break;
                case 4 : courses = Arrays.asList("Math 4", "Object-Oriented Programming", "Circuit Theory", "Signals & Systems", "Material Science");
                break;
                case 5 : courses = Arrays.asList("Microprocessors", "Database Systems", "Control Systems", "Computer Organization", "Numerical Analysis");
                break;
                case 6 :courses = Arrays.asList("Operating Systems", "Communication Theory", "Software Engineering 1", "Electromagnetic Fields", "Engineering Economy");
                break;
                case 7 : courses = Arrays.asList("Computer Networks", "Artificial Intelligence", "Digital Signal Processing", "Software Engineering 2", "Technical Writing");
                break;
                case 8 : courses = Arrays.asList("Embedded Systems", "Distributed Systems", "VLSI Design", "Cloud Computing", "Project Management");
                break;
                case 9 : courses = Arrays.asList("Graduation Project 1", "Machine Learning", "Network Security", "Professional Ethics", "Elective 1");
                break;
                case 10 : courses = Arrays.asList("Graduation Project 2", "Advanced Topics in Engineering", "Industrial Training", "Elective 2");
                break;
            }
        } else if (facultyKey.equals("computer science") || facultyKey.equals("computerscience") || facultyKey.equals("cs")) {
            switch (semester) {
                case 1: courses = Arrays.asList("Intro to CS", "Discrete Math", "Math 1", "Academic Writing");
                break;
                case 2 : courses = Arrays.asList("Structured Programming (C)", "Math 2", "Logic Design", "Physics");
                break;
                case 3 : courses = Arrays.asList("Data Structures", "OOP (C++)", "Calculus", "Electronics");
                break;
                case 4: courses = Arrays.asList("Java Web Development", "Computer Org", "Software Eng 1", "Linear Algebra");
                break;
                case 5 :courses = Arrays.asList("Operating Systems", "Database Systems", "Theory of Computation", "Algorithms");
                break;
                case 6 :courses = Arrays.asList("Software Eng 2", "Computer Networks", "AI", "Mobile Development");
                break;
                case 7: courses = Arrays.asList("Graduation Project I", "Cyber Security", "Machine Learning", "Cloud Computing");
                break;
                case 8:courses = Arrays.asList("Graduation Project II", "Professional Ethics", "Big Data", "Image Processing");
                break;
            }
        } else if (facultyKey.equals("business")) {
            switch (semester) {
                case 1:courses = Arrays.asList("Intro to Management", "Microeconomics", "Business Math", "Accounting 1");
                break;
                case 2:courses = Arrays.asList("Macroeconomics", "Marketing Basics", "Business Statistics", "Accounting 2");
                break;
                case 3 :courses = Arrays.asList("Organizational Behavior", "Financial Management", "Business Law", "HR Management");
                break;
                case 4 : courses = Arrays.asList("Operations Management", "E-Business", "Consumer Behavior", "Corporate Finance");
                break;
                case 5 :courses = Arrays.asList("Strategic Management", "Entrepreneurship", "Supply Chain", "International Business");
                break;
                case 6 :courses = Arrays.asList("Graduation Project", "Management Information Systems", "Ethics");
                break;
            }
        }

        float gpa = 0.0f;
        response.put("studentName", name);
        response.put("gpa", gpa);
        response.put("semester", semester);
        response.put("enrolledCourses", courses);
        response.put("faculty", faculty);
        response.put("message", "Welcome back, " + name);
        return response;
    }
}


