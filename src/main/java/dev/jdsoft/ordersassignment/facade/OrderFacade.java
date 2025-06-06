package dev.jdsoft.ordersassignment.facade;

import dev.jdsoft.ordersassignment.api.request.CreateOrderRequestModel;
import dev.jdsoft.ordersassignment.api.request.GetOrdersRequestModel;
import dev.jdsoft.ordersassignment.api.response.OrderResponseModel;
import dev.jdsoft.ordersassignment.converter.OrderConverter;
import dev.jdsoft.ordersassignment.persistence.dao.OrderRepository;
import dev.jdsoft.ordersassignment.persistence.entity.OrderProduct;
import dev.jdsoft.ordersassignment.persistence.entity.Product;
import dev.jdsoft.ordersassignment.persistence.entity.User;
import dev.jdsoft.ordersassignment.service.OrderProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderRepository orderRepository;
    private final OrderProductsService orderProductsService;

    public List<OrderResponseModel> getOrders(GetOrdersRequestModel requestModel) {
        var orders = orderRepository.findAllByCreationTimeBetween(requestModel.getFromDateTime(), requestModel.getToDateTime());
        return OrderConverter.createResponseModel(orders);
    }

    @Transactional
    public OrderResponseModel createOrder(CreateOrderRequestModel requestModel) {
        var user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        var productQuantities = orderProductsService.merge(requestModel.getProducts());

        var productsMap = orderProductsService.fetch(productQuantities)
                .stream().collect(Collectors.toMap(Product::getId, p -> p));

        var orderProducts = productQuantities.stream()
                .map(productQuantity -> new OrderProduct(productsMap.get(productQuantity.getProductId()), productQuantity.getQuantity()))
                .collect(Collectors.toList());

        var entity = OrderConverter.createEntity(orderProducts, user);
        var created = orderRepository.save(entity);

        return OrderConverter.createResponseModel(created);
    }
}
