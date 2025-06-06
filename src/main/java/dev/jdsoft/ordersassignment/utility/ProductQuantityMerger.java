package dev.jdsoft.ordersassignment.utility;

import dev.jdsoft.ordersassignment.api.request.ProductQuantityRequestModel;

import java.util.List;
import java.util.stream.Collectors;

public class ProductQuantityMerger {

    public static List<ProductQuantityRequestModel> merge(List<ProductQuantityRequestModel> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        ProductQuantityRequestModel::getProductId,
                        Collectors.summingLong(ProductQuantityRequestModel::getQuantity)
                ))
                .entrySet().stream()
                .map(entry -> {
                    ProductQuantityRequestModel model = new ProductQuantityRequestModel();
                    model.setProductId(entry.getKey());
                    model.setQuantity(entry.getValue());
                    return model;
                })
                .collect(Collectors.toList());
    }
}
