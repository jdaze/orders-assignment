package dev.jdsoft.ordersassignment.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import dev.jdsoft.ordersassignment.api.request.CreateProductRequestModel;
import dev.jdsoft.ordersassignment.api.request.UpdateProductRequestModel;
import dev.jdsoft.ordersassignment.api.response.ProductResponseModel;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static dev.jdsoft.ordersassignment.exceptions.ErrorCode.PRODUCT_ALREADY_EXISTS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestContainersConfiguration.class)
public class ProductControllerIntegrationTest extends AbstractControllerTest {

    @Test
    void shouldCreateAProduct() throws Exception {
        var name = UUID.randomUUID().toString();
        var price = BigDecimal.valueOf(100.52);
        var response = createProduct(name, price);

        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo(name);
        assertThat(response.getPriceInEuros()).isEqualTo(price);
    }

    @Test
    void shouldFindAllProducts() throws Exception {
        var name1 = UUID.randomUUID().toString();
        var price1 = BigDecimal.valueOf(100.52);
        var name2 = UUID.randomUUID().toString();
        var price2 = BigDecimal.valueOf(50.23);

        var product1 = createProduct(name1, price1);
        var product2 = createProduct(name2, price2);

        var jsonResponse = mockMvc.perform(get("/products")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn().getResponse().getContentAsString();

        var result = objectMapper.readValue(jsonResponse, new TypeReference<List<ProductResponseModel>>() {
        });

        assertThat(result).containsAll(List.of(product1, product2));
    }

    @Test
    void shouldNotCreateAProductDuplicateName() throws Exception {
        var name1 = UUID.randomUUID().toString();
        var price1 = BigDecimal.valueOf(100.52);

        createProduct(name1, price1);

        var request = new CreateProductRequestModel();
        request.setName(name1);
        request.setPriceInEuros(price1);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(PRODUCT_ALREADY_EXISTS.getCode()))
                .andExpect(jsonPath("$.message").value(PRODUCT_ALREADY_EXISTS.getMessage()));
    }

    @Test
    void shouldUpdateProductPrice() throws Exception {
        var name1 = UUID.randomUUID().toString();
        var price1 = BigDecimal.valueOf(100.52);
        var price2 = BigDecimal.valueOf(150.52);

        var product = createProduct(name1, price1);

        var request = new UpdateProductRequestModel();
        request.setName(name1);
        request.setPriceInEuros(price2);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(put("/products/" + product.getId())
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.priceInEuros").value(price2));
    }

    private ProductResponseModel createProduct(String name, BigDecimal priceInEuros) throws Exception {
        var request = new CreateProductRequestModel();
        request.setName(name);
        request.setPriceInEuros(priceInEuros);

        var json = objectMapper.writeValueAsString(request);

        var result = mockMvc.perform(post("/products")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        var response = result.getResponse().getContentAsString();

        return objectMapper.readValue(response, ProductResponseModel.class);
    }
}
