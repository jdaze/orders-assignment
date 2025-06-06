package dev.jdsoft.ordersassignment.api.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "creationTime")
@ToString
public class OrderResponseModel {

    private Long id;
    private List<ProductQuantityResponseModel> products;
    private BigDecimal priceInEuros;
    private LocalDateTime creationTime;
}
