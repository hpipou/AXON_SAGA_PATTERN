package fr.france.banque.sagas.controllers;

import fr.france.banque.sagas.commands.CreateOrderCommand;
import fr.france.banque.sagas.dtos.OrderCreateDTO;
import fr.france.banque.sagas.enums.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping(value = "/api/orders")
public class OrderCommandController {

    private CommandGateway commandGateway;

    public OrderCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public CompletableFuture<String> createOrder(@RequestBody OrderCreateDTO orderCreateDTO){

        return commandGateway.send(new CreateOrderCommand(
                UUID.randomUUID().toString(),
                orderCreateDTO.getItemType(),
                orderCreateDTO.getPrice(),
                orderCreateDTO.getCurrency(),
                String.valueOf(OrderStatus.CREATED)
        ));

    }
}
