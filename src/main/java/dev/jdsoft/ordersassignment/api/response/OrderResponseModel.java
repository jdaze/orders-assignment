package dev.jdsoft.ordersassignment.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseModel {

    private List<ProductQuantityResponseModel> products;
    private BigDecimal priceInEuros;
    private LocalDateTime creationTime;
}
