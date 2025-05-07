package ua.karazin.moviesorderservice.order;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.karazin.moviesbaseevents.order.revision1.OrderCreateRequest1;
import ua.karazin.moviesorderservice.order.aggregate.Order;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
  private final CommandGateway commandGateway;
  private final OrderMapper orderMapper;

  @PostMapping
  public CompletableFuture<Order> createOrder(@Valid @RequestBody OrderCreateRequest1 request) {
    var command = orderMapper.createOrderCommand(request);
    return commandGateway.send(command);
  }
}
