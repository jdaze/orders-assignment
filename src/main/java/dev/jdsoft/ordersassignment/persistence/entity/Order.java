package dev.jdsoft.ordersassignment.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @CreationTimestamp
    @Column(name = "creation_time", nullable = false)
    private LocalDateTime creationTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

    @Column(name = "price_in_euros", nullable = false)
    private BigDecimal priceInEuros;

    public Order(User user, List<OrderProduct> orderProducts, BigDecimal priceInEuros) {
        this.user = user;
        this.priceInEuros = priceInEuros;
        this.setOrderProducts(orderProducts);
    }

    private void setOrderProducts(List<OrderProduct> orderProducts) {
        orderProducts.forEach(op -> op.setOrder(this));
        this.orderProducts = orderProducts;
    }
}