package dev.jdsoft.ordersassignment.exceptions;

import lombok.Getter;

@Getter
public enum ErrorCode {

    GENERIC_ERROR("ERROR_100000", "Error occurred.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
