package dev.jdsoft.ordersassignment.persistence.dao;

import dev.jdsoft.ordersassignment.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}