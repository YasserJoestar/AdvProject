package com.example.simpleportal.Service;

import com.example.simpleportal.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
            select b from Book b
            where (:q is null or :q = '' or lower(b.title) like lower(concat('%', :q, '%')) or lower(b.author) like lower(concat('%', :q, '%')))
            order by b.title asc
            """)
    List<Book> search(@Param("q") String q);
}

