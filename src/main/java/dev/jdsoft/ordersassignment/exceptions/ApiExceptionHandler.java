package dev.jdsoft.ordersassignment.exceptions;

import dev.jdsoft.ordersassignment.api.response.ErrorResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(OperationFailedException.class)
    public ResponseEntity<ErrorResponseModel> handleOperationFailed(OperationFailedException ex) {
        log.error(ex.getErrorCode().getMessage(), ex);
        var errorCode = ex.getErrorCode();
        return ResponseEntity.badRequest().body(
                new ErrorResponseModel(LocalDateTime.now(), errorCode.getCode(), errorCode.getMessage(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseModel> handleOtherExceptions(Exception ex) {
        log.error(ex.getMessage(), ex);
        var errorCode = ErrorCode.GENERIC_ERROR;
        return ResponseEntity.internalServerError().body(
                new ErrorResponseModel(LocalDateTime.now(), errorCode.getCode(), errorCode.getMessage(), ex.getMessage()));
    }
}