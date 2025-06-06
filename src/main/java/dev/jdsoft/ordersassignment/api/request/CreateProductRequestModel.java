package dev.jdsoft.ordersassignment.api.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateProductRequestModel {

    @NotBlank
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Min(value = 0, message = "Price should be 0 or greater")
    private BigDecimal priceInEuros;
}
