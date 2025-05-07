package ua.karazin.moviesorderservice.profile;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.karazin.moviesorderservice.order.query.OrderRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileService {
  private final ProfileRepository profileRepository;
  private final OrderRepository orderRepository;

  public Optional<Profile> findByOrderId(String orderId) {
    var order = orderRepository.findById(orderId).orElseThrow();
    return profileRepository.findById(order.getProfileId());
  }

  public Optional<Profile> findById(String id) {
    return profileRepository.findById(id);
  }

  public Profile save(Profile profile) {
    return profileRepository.save(profile);
  }
}
