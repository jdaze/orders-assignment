package dev.jdsoft.ordersassignment.api.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
public class ProductResponseModel {

    private Long id;

    private String name;

    private BigDecimal priceInEuros;

}
