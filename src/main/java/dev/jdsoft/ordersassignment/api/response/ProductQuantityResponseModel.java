package dev.jdsoft.ordersassignment.api.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
@NoArgsConstructor
public class ProductQuantityResponseModel {

    private ProductResponseModel product;
    private BigDecimal completePriceInEuros;
    private Long quantity;

    public ProductQuantityResponseModel(ProductResponseModel product, BigDecimal completePriceInEuros, Long quantity) {
        this.product = product;
        this.completePriceInEuros = completePriceInEuros.setScale(2, RoundingMode.HALF_UP);
        this.quantity = quantity;
    }
}
