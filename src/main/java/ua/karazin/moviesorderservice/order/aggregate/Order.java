package ua.karazin.moviesorderservice.order.aggregate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventProcessingException;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.spring.stereotype.Aggregate;
import ua.karazin.moviesbaseevents.order.revision1.*;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@Data
@NoArgsConstructor
public class Order {
    @AggregateIdentifier
    private String orderId;

    @NotNull
    @AggregateMember
    private Movie movie;

    @NotBlank
    private String profileId;

    @NotNull
    private OrderStatus1 status;

    @CommandHandler
    private Order(CreateOrderCommand1 command) {
        apply(new OrderCreatedEvent1(command.orderId(), command.orderDto()));
    }

    @CommandHandler
    private void handle(CompleteOrderCommand1 command) {
        apply(new OrderCompletedEvent1(command.orderId(), command.finalPrice()));
    }

    @EventSourcingHandler
    private void on(OrderCreatedEvent1 event) {
        this.orderId = event.orderId();
        this.movie = new Movie();
        this.profileId = event.orderDto().profileId();
        this.status = OrderStatus1.PENDING;
    }

    @EventSourcingHandler
    private void on(OrderCompletedEvent1 event) {
        throwIfIsSettled();
        this.status = OrderStatus1.COMPLETED;
    }

    @EventSourcingHandler
    private void on(OrderRejectedByPaymentEvent1 event) {
        throwIfIsSettled();
        this.status = OrderStatus1.REJECTED_BY_PAYMENT;
    }

    private void throwIfIsSettled() {
        if (isSettled()) {
            throw new EventProcessingException("Order has already been settled", new IllegalStateException());
        }
    }

    private boolean isSettled() {
      return !this.status.equals(OrderStatus1.PENDING);
    }
}
