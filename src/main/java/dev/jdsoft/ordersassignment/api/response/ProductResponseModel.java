package dev.jdsoft.ordersassignment.api.response;

import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ProductResponseModel {

    private Long id;

    private String name;

    private BigDecimal priceInEuros;

    public ProductResponseModel(Long id, String name, BigDecimal priceInEuros) {
        this.id = id;
        this.name = name;
        this.priceInEuros = priceInEuros.setScale(2, RoundingMode.HALF_UP);
    }
}
