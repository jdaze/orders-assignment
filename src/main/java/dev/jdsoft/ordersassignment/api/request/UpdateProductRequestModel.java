package dev.jdsoft.ordersassignment.api.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Digits;
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
public class UpdateProductRequestModel {

    @NotBlank
    @Size(max = 255, message = "Name cannot exceed 255 characters")
    private String name;

    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Price should be have maximum of 2 fractional digits, eg. 100.52")
    @Min(value = 0, message = "Price should be 0 or greater")
    private BigDecimal priceInEuros;

    @JsonIgnore
    @Setter
    private Long id;
}
