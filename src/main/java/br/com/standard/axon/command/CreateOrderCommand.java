package br.com.standard.axon.command;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class CreateOrderCommand {

    @TargetAggregateIdentifier
    private final String orderID;
    private final String productID;

}
