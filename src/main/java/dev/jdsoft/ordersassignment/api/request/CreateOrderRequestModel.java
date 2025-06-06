package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderRequestModel {

    @NotEmpty
    @Valid
    private List<ProductQuantityRequestModel> products;
}
