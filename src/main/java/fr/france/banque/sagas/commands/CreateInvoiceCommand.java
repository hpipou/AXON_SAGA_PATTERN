package fr.france.banque.sagas.commands;

import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateInvoiceCommand{

    @TargetAggregateIdentifier
    @Getter private String paymentId;
    @Getter private String orderId;

    public CreateInvoiceCommand(String paymentId, String orderId) {
        this.paymentId = paymentId;
        this.orderId = orderId;
    }
}
