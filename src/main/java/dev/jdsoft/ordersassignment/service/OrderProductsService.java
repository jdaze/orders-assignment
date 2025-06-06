package dev.jdsoft.ordersassignment.service;

import dev.jdsoft.ordersassignment.api.request.ProductQuantityRequestModel;
import dev.jdsoft.ordersassignment.exceptions.ErrorCode;
import dev.jdsoft.ordersassignment.exceptions.OperationFailedException;
import dev.jdsoft.ordersassignment.persistence.dao.ProductRepository;
import dev.jdsoft.ordersassignment.persistence.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderProductsService {

    private final ProductRepository productRepository;

    public List<Product> fetch(List<ProductQuantityRequestModel> products) {
        var ids = products.stream().map(ProductQuantityRequestModel::getProductId).collect(Collectors.toSet());
        var found = productRepository.findAllById(ids);

        if (ids.size() != found.size()) {
            throw new OperationFailedException(ErrorCode.SOME_PRODUCTS_DONT_EXIST);
        }
        return found;
    }
}
