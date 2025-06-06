package dev.jdsoft.ordersassignment.controller;

import dev.jdsoft.ordersassignment.api.request.CreateProductRequestModel;
import dev.jdsoft.ordersassignment.api.request.UpdateProductRequestModel;
import dev.jdsoft.ordersassignment.api.response.ProductResponseModel;
import dev.jdsoft.ordersassignment.facade.ProductFacade;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductFacade productFacade;

    @Operation(summary = "Get all products.", tags = "PRODUCT")
    @GetMapping
    public ResponseEntity<List<ProductResponseModel>> getAllProducts() {
        var products = productFacade.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "Create a product.", tags = "PRODUCT")
    @PostMapping
    public ResponseEntity<ProductResponseModel> createProduct(@Valid @RequestBody CreateProductRequestModel requestModel) {
        var product = productFacade.createProduct(requestModel);
        return ResponseEntity.ok(product);
    }

    @Operation(summary = "Update a product.", tags = "PRODUCT")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseModel> updateProduct(@PathVariable("id") Long productId, @Valid @RequestBody UpdateProductRequestModel requestModel) {
        requestModel.setId(productId);

        var product = productFacade.updateProduct(requestModel);
        return ResponseEntity.ok(product);
    }
}
