package ua.karazin.moviesorderservice.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;

  public Optional<Order> findById(String id) {
    return orderRepository.findById(id);
  }
}
