package dev.jdsoft.ordersassignment.api.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseModel {

    private LocalDateTime timestamp;
    private String errorCode;
    private String message;
    private String cause;
}
