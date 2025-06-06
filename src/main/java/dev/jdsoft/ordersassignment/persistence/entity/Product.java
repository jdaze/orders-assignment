package dev.jdsoft.ordersassignment.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "price_in_euros", nullable = false)
    private BigDecimal priceInEuros;

    @Builder.Default
    @Column(name = "archived", nullable = false)
    private boolean archived = false;

    public Product(String name, BigDecimal priceInEuros) {
        this.name = name;
        this.priceInEuros = priceInEuros;
    }
}
