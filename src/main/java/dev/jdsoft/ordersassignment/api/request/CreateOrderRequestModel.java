package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class CreateOrderRequestModel {

    @NotEmpty
    private List<ProductQuantityRequestModel> products;
}
