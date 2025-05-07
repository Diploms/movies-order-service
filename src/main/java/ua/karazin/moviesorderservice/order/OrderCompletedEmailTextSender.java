package ua.karazin.moviesorderservice.order;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.ReplayStatus;
import org.springframework.stereotype.Component;
import ua.karazin.moviesbaseevents.order.revision1.OrderCompletedEvent1;
import ua.karazin.moviesorderservice.service.mail.EmailService;

@Component
@RequiredArgsConstructor
public class OrderCompletedEmailTextSender {
  private final EmailService emailService;
  private final OrderEmailDetailsCreator orderEmailDetailsCreator;

  @EventHandler
  private void on(OrderCompletedEvent1 event, ReplayStatus status) {
    if (status.isReplay()) {
      return;
    }

    var details = orderEmailDetailsCreator.createEmailDetails(event.orderId());
    emailService.sendEmail(details);
  }
}
