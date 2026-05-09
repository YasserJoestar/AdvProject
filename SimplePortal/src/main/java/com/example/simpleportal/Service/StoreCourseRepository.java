package com.example.simpleportal.Service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.simpleportal.Model.StoreCourse;

import java.util.List;

public interface StoreCourseRepository extends JpaRepository<StoreCourse, Long> {
    @Query("""
            select c from StoreCourse c
            where (:q is null or :q = '' or lower(c.name) like lower(concat('%', :q, '%')) or lower(c.code) like lower(concat('%', :q, '%')))
            order by c.name asc
            """)
    List<StoreCourse> search(@Param("q") String q);
}

