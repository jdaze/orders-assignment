package dev.jdsoft.ordersassignment.controller;

import dev.jdsoft.ordersassignment.api.request.CreateOrderRequestModel;
import dev.jdsoft.ordersassignment.api.request.ProductQuantityRequestModel;
import dev.jdsoft.ordersassignment.api.response.OrderResponseModel;
import dev.jdsoft.ordersassignment.persistence.dao.ProductRepository;
import dev.jdsoft.ordersassignment.persistence.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static dev.jdsoft.ordersassignment.exceptions.ErrorCode.SOME_PRODUCTS_DONT_EXIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import(TestContainersConfiguration.class)
public class OrderControllerIntegrationTest extends AbstractControllerTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldCreateAnOrder() throws Exception {
        var allProducts = productRepository.findAll();

        var quantities = prepareProductQuantities(allProducts);
        var expectedPrice = calculateExpectedPrice(allProducts, quantities);

        var response = createOrder(quantities);

        assertThat(response).isNotNull();
        assertThat(response.getProducts()).hasSameSizeAs(quantities);
        assertThat(response.getPriceInEuros()).isEqualTo(expectedPrice);
    }

    @Test
    void shouldFindRecentlyCreatedOrder() throws Exception {
        var allProducts = productRepository.findAll();
        var quantities = prepareProductQuantities(allProducts);

        var order = createOrder(quantities);

        var from = Instant.now().minusSeconds(60).getEpochSecond();
        var to = Instant.now().plusSeconds(60).getEpochSecond();

        mockMvc.perform(get("/orders")
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].priceInEuros").value(order.getPriceInEuros()));
    }

    @Test
    void shouldNotFindAnyOrder() throws Exception {
        var allProducts = productRepository.findAll();
        var quantities = prepareProductQuantities(allProducts);

        createOrder(quantities);

        var from = Instant.now().minusSeconds(2).getEpochSecond();
        var to = Instant.now().minusSeconds(1).getEpochSecond();

        mockMvc.perform(get("/orders")
                        .param("from", String.valueOf(from))
                        .param("to", String.valueOf(to))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void shouldNotCreateAnOrderFailOnMissingProduct() throws Exception {
        var request = new CreateOrderRequestModel();

        List<ProductQuantityRequestModel> quantities = List.of(new ProductQuantityRequestModel(-1L, 2L));

        request.setProducts(quantities);

        var json = objectMapper.writeValueAsString(request);

        mockMvc.perform(post("/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(SOME_PRODUCTS_DONT_EXIST.getCode()))
                .andExpect(jsonPath("$.message").value(SOME_PRODUCTS_DONT_EXIST.getMessage()));
    }

    private OrderResponseModel createOrder(List<ProductQuantityRequestModel> quantities) throws Exception {
        var request = new CreateOrderRequestModel();
        request.setProducts(quantities);

        var json = objectMapper.writeValueAsString(request);

        var result = mockMvc.perform(post("/orders")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        var response = result.getResponse().getContentAsString();

        return objectMapper.readValue(response, OrderResponseModel.class);
    }

    private BigDecimal calculateExpectedPrice(List<Product> allProducts, List<ProductQuantityRequestModel> quantities) {
        var byIds = allProducts.stream().collect(Collectors.toMap(Product::getId, p -> p));

        return quantities.stream().map(quantity -> {
            var price = byIds.get(quantity.getProductId()).getPriceInEuros();
            return price.multiply(BigDecimal.valueOf(quantity.getQuantity()));
        }).reduce(BigDecimal::add).map(bd -> bd.setScale(2, RoundingMode.HALF_UP)).get();
    }

    private static List<ProductQuantityRequestModel> prepareProductQuantities(List<Product> allProducts) {
        return IntStream.range(0, allProducts.size())
                .mapToObj(i -> {
                    Product product = allProducts.get(i);
                    ProductQuantityRequestModel model = new ProductQuantityRequestModel();
                    model.setProductId(product.getId());
                    model.setQuantity((long) (i + 1)); // quantity based on index
                    return model;
                })
                .collect(Collectors.toList());
    }
}
