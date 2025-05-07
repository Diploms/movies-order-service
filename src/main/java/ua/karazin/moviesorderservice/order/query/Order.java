package ua.karazin.moviesorderservice.order.query;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.math.BigDecimal;

@RedisHash("orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
  private String id;

  private String profileId;

  private String movieId;

  private String title;

  private BigDecimal price;
}
