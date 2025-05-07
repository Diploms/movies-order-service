package ua.karazin.moviesorderservice.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.karazin.moviesorderservice.profile.ProfileService;
import ua.karazin.moviesorderservice.service.mail.EmailDetails;

@Service
@RequiredArgsConstructor
public class OrderEmailDetailsCreator {
  private static final String SUBJECT = "Order invoice \"%s\"";

  private final OrderEmailTextCreator textCreator;
  private final ProfileService profileService;

  public EmailDetails createEmailDetails(String orderId) {
    var profile = profileService.findByOrderId(orderId).orElseThrow();

    var email = profile.getEmail();
    var text = textCreator.createText(orderId);
    var formattedSubject = SUBJECT.formatted(orderId);
    return new EmailDetails(email, formattedSubject, text);
  }
}
