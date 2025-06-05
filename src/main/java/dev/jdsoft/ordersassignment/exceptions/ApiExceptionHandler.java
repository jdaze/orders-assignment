package dev.jdsoft.ordersassignment.exceptions;

import dev.jdsoft.ordersassignment.api.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<?> handleOperationFailed(OperationFailedException ex) {
        var errorCode = ex.getErrorCode();
        return ResponseEntity.internalServerError().body(
                new ErrorResponse(LocalDateTime.now(), errorCode.getCode(), errorCode.getMessage(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleOtherExceptions(Exception ex) {
        var errorCode = ErrorCode.GENERIC_ERROR;
        return ResponseEntity.internalServerError().body(
                new ErrorResponse(LocalDateTime.now(), errorCode.getCode(), errorCode.getMessage(), ex.getMessage()));
    }
}