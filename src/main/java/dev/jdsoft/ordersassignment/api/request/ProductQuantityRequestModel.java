package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductQuantityRequestModel {

    @NotNull
    private Long productId;

    @Min(1)
    private Long quantity;
}
