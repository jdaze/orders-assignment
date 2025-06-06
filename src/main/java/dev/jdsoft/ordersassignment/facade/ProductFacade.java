package dev.jdsoft.ordersassignment.facade;

import dev.jdsoft.ordersassignment.api.request.CreateProductRequestModel;
import dev.jdsoft.ordersassignment.api.request.UpdateProductRequestModel;
import dev.jdsoft.ordersassignment.api.response.ProductResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductFacade {

    public List<ProductResponseModel> getAllProducts() {
        return null;
    }

    @Transactional
    public ProductResponseModel createProduct(CreateProductRequestModel requestModel) {
        return null;
    }

    @Transactional
    public ProductResponseModel updateProduct(UpdateProductRequestModel requestModel) {
        return null;
    }
}
