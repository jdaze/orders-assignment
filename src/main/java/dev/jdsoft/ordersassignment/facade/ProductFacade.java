package dev.jdsoft.ordersassignment.facade;

import dev.jdsoft.ordersassignment.api.request.CreateProductRequestModel;
import dev.jdsoft.ordersassignment.api.request.UpdateProductRequestModel;
import dev.jdsoft.ordersassignment.api.response.ProductResponseModel;
import dev.jdsoft.ordersassignment.converter.ProductConverter;
import dev.jdsoft.ordersassignment.exceptions.ErrorCode;
import dev.jdsoft.ordersassignment.exceptions.OperationFailedException;
import dev.jdsoft.ordersassignment.persistence.dao.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductFacade {

    private final ProductRepository productRepository;

    public List<ProductResponseModel> getAllProducts() {
        var products = productRepository.findAll();
        return ProductConverter.createResponseModel(products);
    }

    @Transactional
    public ProductResponseModel createProduct(CreateProductRequestModel requestModel) {
        var found = productRepository.findByName(requestModel.getName());

        if (found.isPresent()) {
            throw new OperationFailedException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        var entity = ProductConverter.createEntity(requestModel);
        var created = productRepository.save(entity);
        return ProductConverter.createResponseModel(created);
    }

    @Transactional
    public ProductResponseModel updateProduct(UpdateProductRequestModel requestModel) {
        var toUpdate = productRepository.findById(requestModel.getId());

        if (toUpdate.isEmpty()) {
            throw new OperationFailedException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        var found = productRepository.findByName(requestModel.getName());

        if (found.isPresent() && !found.get().getId().equals(requestModel.getId())) {
            throw new OperationFailedException(ErrorCode.PRODUCT_ALREADY_EXISTS);
        }

        var product = toUpdate.get();

        product.setName(requestModel.getName());
        product.setPriceInEuros(requestModel.getPriceInEuros());
        var updated = productRepository.save(product);

        return ProductConverter.createResponseModel(updated);
    }
}
