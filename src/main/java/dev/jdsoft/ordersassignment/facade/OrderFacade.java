package dev.jdsoft.ordersassignment.facade;

import dev.jdsoft.ordersassignment.api.request.CreateOrderRequestModel;
import dev.jdsoft.ordersassignment.api.request.GetOrdersRequestModel;
import dev.jdsoft.ordersassignment.api.response.OrderResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderFacade {

    public List<OrderResponseModel> getOrders(GetOrdersRequestModel requestModel) {
        return null;
    }

    @Transactional
    public OrderResponseModel createOrder(CreateOrderRequestModel requestModel) {
        return null;
    }
}
