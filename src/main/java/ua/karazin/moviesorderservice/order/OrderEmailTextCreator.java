package ua.karazin.moviesorderservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.karazin.moviesorderservice.order.query.OrderRepository;
import ua.karazin.moviesorderservice.profile.ProfileService;

@Component
@RequiredArgsConstructor
public class OrderEmailTextCreator {
  private final ProfileService profileService;
  private final OrderRepository orderRepository;

  public String createText(String orderId) {
    var order = orderRepository.findById(orderId).orElseThrow();

    var profileId = profileService.findById(order.getProfileId()).orElseThrow().getId();
    var movieTitle = order.getTitle();
    var moviePrice = order.getPrice();

    return """
        Thanks for your purchase!

        Profile ID:  %s
        Order ID:    %s
        Title:       %s
        Price:       %.2f

        Enjoy your movie!
        """.formatted(
        profileId,
        orderId,
        movieTitle,
        moviePrice
    );
  }
}
