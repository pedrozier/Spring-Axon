package br.com.standard.axon.web.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.standard.axon.command.ConfirmOrderCommand;
import br.com.standard.axon.command.CreateOrderCommand;
import br.com.standard.axon.command.ShipOrderCommand;
import br.com.standard.axon.query.FindAllOrderedProductsQuery;
import br.com.standard.axon.query.Order;
import br.com.standard.axon.services.OrdersEventHandler;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("orders")
public class OrderRestEndpoint {

    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    @Autowired
    OrdersEventHandler ordersHandler;

    @PostMapping("/ship-order")
    public CompletableFuture<Void> shipOrder() {
        String orderID = UUID.randomUUID().toString();
        return commandGateway.send(new CreateOrderCommand(orderID, "teste"))
                .thenCompose(result -> commandGateway.send(new ConfirmOrderCommand(orderID)))
                .thenCompose(result -> commandGateway.send(new ShipOrderCommand(orderID)));

    }

    @GetMapping("/all-orders")
    public CompletableFuture<List<Order>> findAllOrders() {
        return queryGateway.query(new FindAllOrderedProductsQuery(), ResponseTypes.multipleInstancesOf(Order.class));
    }

}
