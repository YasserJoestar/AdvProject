package com.example.simpleportal.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "enrollments",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_enrollment_student_course_semester",
                columnNames = {"student_id", "catalog_course_id", "semester"}
        )
)
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "catalog_course_id", nullable = false)
    private CatalogCourse catalogCourse;

    @Column(nullable = false)
    private String semester;

    private String grade;

    public Enrollment() {}

    public Enrollment(Student student, CatalogCourse catalogCourse, String semester, String grade) {
        this.student = student;
        this.catalogCourse = catalogCourse;
        this.semester = semester;
        this.grade = grade;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public CatalogCourse getCatalogCourse() {
        return catalogCourse;
    }

    public void setCatalogCourse(CatalogCourse catalogCourse) {
        this.catalogCourse = catalogCourse;
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
}

