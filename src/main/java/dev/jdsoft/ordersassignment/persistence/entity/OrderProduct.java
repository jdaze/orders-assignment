package dev.jdsoft.ordersassignment.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_product", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"order_id", "product_id"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private Long quantity;

    @Column(name = "price_in_euros", nullable = false)
    private BigDecimal priceInEuros;

    @Column(name = "complete_price_in_euros", nullable = false)
    private BigDecimal completePriceInEuros;

    public OrderProduct(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
        this.priceInEuros = product.getPriceInEuros();
        this.completePriceInEuros = calculatePrice();
    }

    private BigDecimal calculatePrice() {
        return product.getPriceInEuros().multiply(BigDecimal.valueOf(quantity));
    }
}