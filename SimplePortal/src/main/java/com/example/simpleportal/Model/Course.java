package com.example.simpleportal.Model;

public class Course {
    private Long id;

    private Long studentId;
    private String courseName;
    private String courseCode;
    private String instructor;
    private String semester;
    private String grade;
    private Integer creditHours;

    public Course() {}

    public Course(Long studentId, String courseName, String courseCode, String instructor, String semester, String grade, Integer creditHours) {
        this.studentId = studentId;
        this.courseName = courseName;
        this.courseCode = courseCode;
        this.instructor = instructor;
        this.semester = semester;
        this.grade = grade;
        this.creditHours = creditHours;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getCreditHours() {
        return creditHours;
    }

    public void setCreditHours(Integer creditHours) {
        this.creditHours = creditHours;
    }
}