package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ProductQuantityRequestModel {

    @NotNull
    private Long productId;

    @Min(value = 1, message = "Quantity should be 1 or greater")
    private Long quantity;
}
