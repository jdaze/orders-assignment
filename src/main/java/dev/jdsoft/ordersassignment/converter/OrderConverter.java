package dev.jdsoft.ordersassignment.converter;

import dev.jdsoft.ordersassignment.api.response.OrderResponseModel;
import dev.jdsoft.ordersassignment.api.response.ProductQuantityResponseModel;
import dev.jdsoft.ordersassignment.persistence.entity.Order;
import dev.jdsoft.ordersassignment.persistence.entity.OrderProduct;
import dev.jdsoft.ordersassignment.persistence.entity.User;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static Order createEntity(List<OrderProduct> orderProducts, User user) {
        var priceInEuros = calculateOrderPrice(orderProducts);
        return new Order(user, orderProducts, priceInEuros);
    }

    public static OrderResponseModel createResponseModel(Order order) {
        var email = order.getUser().getEmail();
        var productQuantities = order.getOrderProducts().stream()
                .map(OrderConverter::convertOrderProduct).collect(Collectors.toList());
        return new OrderResponseModel(order.getId(), productQuantities, order.getPriceInEuros(), email, order.getCreationTime());
    }

    public static List<OrderResponseModel> createResponseModel(List<Order> orders) {
        return orders.stream().map(OrderConverter::createResponseModel).collect(Collectors.toList());
    }

    private static ProductQuantityResponseModel convertOrderProduct(OrderProduct orderProduct) {
        var product = ProductConverter.createResponseModel(orderProduct.getProduct());
        product.setPriceInEuros(orderProduct.getPriceInEuros());
        return new ProductQuantityResponseModel(product, orderProduct.getCompletePriceInEuros(), orderProduct.getQuantity());
    }

    private static BigDecimal calculateOrderPrice(List<OrderProduct> products) {
        return products.stream().map(OrderProduct::getCompletePriceInEuros)
                .reduce(BigDecimal::add)
                .map(bd -> bd.setScale(2, RoundingMode.HALF_UP))
                .get();
    }
}
