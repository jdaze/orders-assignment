package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductQuantityRequestModel {

    @NotNull
    private Long productId;

    @Min(1)
    private Long quantity;
}
