package com.example.simpleportal.Service;

import com.example.simpleportal.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStudent_IdOrderByCreatedAtDesc(Long studentId);
}
