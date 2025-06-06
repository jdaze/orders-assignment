package dev.jdsoft.ordersassignment.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

    SOME_PRODUCTS_DONT_EXIST("ERROR_200000", "Some products from order don't exist."),
    PRODUCT_ALREADY_EXISTS("ERROR_200001", "Product with the same name already exists."),
    PRODUCT_NOT_FOUND("ERROR_200002", "Product with the given id has not been found."),
    GENERIC_ERROR("ERROR_100000", "Error occurred.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
