package com.example.simpleportal.Service;

import com.example.simpleportal.Model.CatalogCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CatalogCourseRepository extends JpaRepository<CatalogCourse, Long> {
    Optional<CatalogCourse> findByCourseCodeIgnoreCase(String courseCode);
}

