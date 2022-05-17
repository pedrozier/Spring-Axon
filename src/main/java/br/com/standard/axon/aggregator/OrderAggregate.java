package br.com.standard.axon.aggregator;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import br.com.standard.axon.Exceptions.UnconfirmedOrderException;
import br.com.standard.axon.command.ConfirmOrderCommand;
import br.com.standard.axon.command.CreateOrderCommand;
import br.com.standard.axon.command.ShipOrderCommand;
import br.com.standard.axon.event.OrderConfirmedEvent;
import br.com.standard.axon.event.OrderCreatedEvent;
import br.com.standard.axon.event.OrderShippedEvent;

@Aggregate
public class OrderAggregate {

    @AggregateIdentifier
    private String orderID;

    private boolean orderConfirmed;

    protected OrderAggregate() {
    }

    @CommandHandler
    public OrderAggregate(CreateOrderCommand command) {
        AggregateLifecycle.apply(new OrderCreatedEvent(command.getOrderID(), command.getProductID()));
    }

    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderID = event.getOrderID();
        orderConfirmed = false;
    }

    @CommandHandler
    public void handle(ConfirmOrderCommand command) {
        if (orderConfirmed) {
            return;
        }
        apply(new OrderConfirmedEvent(orderID));
    }

    @CommandHandler
    public void handle(ShipOrderCommand command) throws UnconfirmedOrderException {
        if (!orderConfirmed) {
            throw new UnconfirmedOrderException();
        }
        apply(new OrderShippedEvent(orderID));
    }

    @EventSourcingHandler
    public void on(OrderConfirmedEvent event) {
        orderConfirmed = true;
    }

}
