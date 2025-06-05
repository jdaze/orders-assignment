package dev.jdsoft.ordersassignment.api.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityResponseModel {

    private ProductResponseModel product;

    private int quantity;
}
