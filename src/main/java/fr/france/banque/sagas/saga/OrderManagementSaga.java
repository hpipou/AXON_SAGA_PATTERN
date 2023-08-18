package fr.france.banque.sagas.saga;

import fr.france.banque.sagas.commands.CreateInvoiceCommand;
import fr.france.banque.sagas.commands.CreateShippingCommand;
import fr.france.banque.sagas.commands.UpdateOrderStatusCommand;
import fr.france.banque.sagas.enums.OrderStatus;
import fr.france.banque.sagas.events.InvoiceCreatedEvent;
import fr.france.banque.sagas.events.OrderCreatedEvent;
import fr.france.banque.sagas.events.OrderShippedEvent;
import fr.france.banque.sagas.events.OrderUpdatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.serialization.Revision;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;

@Saga
@Revision("2") // Révision c'est pour indiquer un numéro dans la table SAGA_ENTRY dans la BDD
public class OrderManagementSaga {

    @Autowired
    private transient CommandGateway commandGateway;

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){

        String paymentId = UUID.randomUUID().toString();
        System.out.println("======> orderId ===> " + paymentId);

        SagaLifecycle.associateWith("paymentId", paymentId);

        commandGateway.send(new CreateInvoiceCommand(paymentId, orderCreatedEvent.orderId));

    }


    @SagaEventHandler(associationProperty = "paymentId")
    public void handle(InvoiceCreatedEvent invoiceCreatedEvent){
        String shippingId = UUID.randomUUID().toString();

        System.out.println("======> paymentId ===> " + shippingId);

        //associate Saga with shipping
        SagaLifecycle.associateWith("shipping", shippingId);

        //send the create shipping command
        commandGateway.send(new CreateShippingCommand(shippingId, invoiceCreatedEvent.orderId, invoiceCreatedEvent.paymentId));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent orderShippedEvent){

        System.out.println("======> orderId shipped ===> " + orderShippedEvent.orderId);
        commandGateway.send(new UpdateOrderStatusCommand(orderShippedEvent.orderId, String.valueOf(OrderStatus.SHIPPED)));
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderUpdatedEvent orderUpdatedEvent){
        System.out.println("======> SAGA END ");
        SagaLifecycle.end();
    }


}