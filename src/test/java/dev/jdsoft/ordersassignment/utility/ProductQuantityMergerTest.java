package dev.jdsoft.ordersassignment.utility;

import dev.jdsoft.ordersassignment.api.request.ProductQuantityRequestModel;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductQuantityMergerTest {

    @Test
    public void shouldMergeDuplicatedProductQuantities() {
        var productId1 = 1L;
        var productId2 = 2L;
        var productId3 = 3L;
        var withDuplicates = List.of(
                new ProductQuantityRequestModel(productId1, 3L),
                new ProductQuantityRequestModel(productId2, 5L),
                new ProductQuantityRequestModel(productId3, 4L),
                new ProductQuantityRequestModel(productId2, 3L),
                new ProductQuantityRequestModel(productId3, 1L),
                new ProductQuantityRequestModel(productId1, 1L)
        );

        var expected = List.of(new ProductQuantityRequestModel(productId1, 4L),
                new ProductQuantityRequestModel(productId2, 8L),
                new ProductQuantityRequestModel(productId3, 5L));

        var actual = ProductQuantityMerger.merge(withDuplicates);

        assertThat(actual).isEqualTo(expected);
    }
}
