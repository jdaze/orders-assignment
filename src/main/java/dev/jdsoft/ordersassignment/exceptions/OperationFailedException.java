package dev.jdsoft.ordersassignment.exceptions;

import lombok.Getter;

@Getter
public class OperationFailedException extends RuntimeException {

    private final ErrorCode errorCode;

    public OperationFailedException(ErrorCode errorCode) {
        super();
        this.errorCode = errorCode;
    }
}
