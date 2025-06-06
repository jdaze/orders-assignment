package dev.jdsoft.ordersassignment.converter;

import dev.jdsoft.ordersassignment.api.request.CreateProductRequestModel;
import dev.jdsoft.ordersassignment.api.response.ProductResponseModel;
import dev.jdsoft.ordersassignment.persistence.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductConverter {

    public static Product createEntity(CreateProductRequestModel requestModel) {
        return new Product(requestModel.getName(), requestModel.getPriceInEuros());
    }

    public static ProductResponseModel createResponseModel(Product product) {
        return new ProductResponseModel(product.getId(), product.getName(), product.getPriceInEuros());
    }

    public static List<ProductResponseModel> createResponseModel(List<Product> products) {
        return products.stream().map(ProductConverter::createResponseModel).collect(Collectors.toList());
    }
}
