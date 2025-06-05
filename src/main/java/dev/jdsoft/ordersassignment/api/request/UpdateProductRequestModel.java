package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
public class UpdateProductRequestModel {

    @NotBlank
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    private BigDecimal priceInEuros;
}
