package fr.france.banque.sagas.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class UpdateOrderStatusCommand {

    @TargetAggregateIdentifier
    @Getter private String orderId;

    @Getter private String orderStatus;

    public UpdateOrderStatusCommand(String orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }
}