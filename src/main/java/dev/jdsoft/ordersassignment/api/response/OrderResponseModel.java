package dev.jdsoft.ordersassignment.api.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseModel {

    private List<ProductQuantityResponseModel> products;
    private BigDecimal priceInEuros;
    private LocalDateTime creationTime;
}
