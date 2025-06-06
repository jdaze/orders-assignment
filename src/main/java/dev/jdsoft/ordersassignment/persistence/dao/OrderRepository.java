package dev.jdsoft.ordersassignment.persistence.dao;

import dev.jdsoft.ordersassignment.persistence.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByCreationTimeBetween(LocalDateTime from, LocalDateTime to);
}