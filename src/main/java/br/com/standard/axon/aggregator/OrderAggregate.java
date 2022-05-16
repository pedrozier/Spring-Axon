package br.com.standard.axon.aggregator;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import br.com.standard.axon.command.CreateOrderCommand;
import br.com.standard.axon.event.OrderCreatedEvent;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderID;
    private boolean orderConfirmed;

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(command.getOrderID(), command.getProductID()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderID = event.getOrderID();
        orderConfirmed = false;
    }

    protected OrderAggregate() {
    }

}
