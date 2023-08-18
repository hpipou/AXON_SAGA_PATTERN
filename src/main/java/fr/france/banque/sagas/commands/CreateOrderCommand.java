package fr.france.banque.sagas.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

public class CreateOrderCommand {

    @TargetAggregateIdentifier
    @Getter private String orderId;

    @Getter private String itemType;

    @Getter private BigDecimal price;

    @Getter private String currency;

    @Getter private String orderStatus;

    public CreateOrderCommand(String orderId, String itemType, BigDecimal price, String currency, String orderStatus) {
        this.orderId = orderId;
        this.itemType = itemType;
        this.price = price;
        this.currency = currency;
        this.orderStatus = orderStatus;
    }
}
