package ua.karazin.moviesorderservice.order.query;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;
import ua.karazin.moviesbaseevents.order.revision1.OrderCreatedEvent1;

@Component
@RequiredArgsConstructor
public class OrderProjectionHandler {
  private final OrderRepository orderRepository;

  @EventHandler
  private void on(OrderCreatedEvent1 event) {
    orderRepository.save(Order.builder()
            .id(event.orderId())
            .profileId(event.orderDto().profileId())
            .movieId(event.orderDto().movieId())
            .title(event.orderDto().title())
            .price(event.orderDto().price())
        .build());
  }
}
