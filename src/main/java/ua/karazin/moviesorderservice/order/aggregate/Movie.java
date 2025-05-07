package ua.karazin.moviesorderservice.order.aggregate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;
import ua.karazin.moviesbaseevents.order.revision1.OrderCreatedEvent1;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class Movie {
    @EntityId
    private String movieId;
    private String title;
    private BigDecimal price;

    @EventSourcingHandler
    private void on(OrderCreatedEvent1 event) {
        var dto = event.orderDto();
        this.movieId = dto.movieId();
        this.title = event.orderDto().title();
        this.price = event.orderDto().price();
    }
}
