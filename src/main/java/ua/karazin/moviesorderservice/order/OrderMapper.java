package ua.karazin.moviesorderservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.karazin.moviesbaseevents.order.revision1.CreateOrderCommand1;
import ua.karazin.moviesbaseevents.order.revision1.OrderCreateRequest1;
import ua.karazin.moviesbaseevents.order.revision1.OrderDto1;
import ua.karazin.moviesorderservice.movie.MovieRepository;
import ua.karazin.moviesorderservice.profile.ProfileRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderMapper {
  private final MovieRepository movieRepository;
  private final ProfileRepository profileRepository;

  public CreateOrderCommand1 createOrderCommand(OrderCreateRequest1 request) {
    var movie = movieRepository.findById(request.movieId()).orElseThrow();
    var accountId = profileRepository.findById(request.profileId()).orElseThrow().getAccountId();

    if (accountId == null) {
      throw new IllegalStateException("Non-confirmed profile can't place an order");
    }

    var randomUuid = UUID.randomUUID().toString();

    var dto = new OrderDto1(randomUuid, movie.getId(), request.profileId(),
        accountId, movie.getTitle(), movie.getPrice());
    return new CreateOrderCommand1(randomUuid, dto);
  }
}
