package dev.jdsoft.ordersassignment.controller;

import dev.jdsoft.ordersassignment.api.request.CreateOrderRequestModel;
import dev.jdsoft.ordersassignment.api.request.GetOrdersRequestModel;
import dev.jdsoft.ordersassignment.api.response.OrderResponseModel;
import dev.jdsoft.ordersassignment.facade.OrderFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @Parameter(name = "from", description = "Start date in epoch seconds", example = "1748809383")
    @Parameter(name = "to", description = "End date in epoch seconds", example = "1749154983")
    @Operation(summary = "Get orders between dates.", tags = "ORDER")
    @GetMapping
    public ResponseEntity<List<OrderResponseModel>> getOrders(@Valid @ParameterObject GetOrdersRequestModel requestModel) {
        var orders = orderFacade.getOrders(requestModel);
        return ResponseEntity.ok(orders);
    }

    @Operation(summary = "Create an order.", tags = "ORDER")
    @PostMapping
    public ResponseEntity<OrderResponseModel> createOrder(@Valid @RequestBody CreateOrderRequestModel requestModel) {
        var order = orderFacade.createOrder(requestModel);
        return ResponseEntity.ok(order);
    }
}
