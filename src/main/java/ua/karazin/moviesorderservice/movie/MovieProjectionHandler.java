package ua.karazin.moviesorderservice.movie;

import lombok.RequiredArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.karazin.moviesbaseevents.movies.revision2.MovieCreatedEvent2;
import ua.karazin.moviesbaseevents.movies.revision2.MovieDeletedEvent2;
import ua.karazin.moviesbaseevents.movies.revision2.MovieUpdatedEvent2;

@Service
@RequiredArgsConstructor
public class MovieProjectionHandler {
  private final MovieRepository repository;

  @EventHandler
  public void on(MovieCreatedEvent2 event) {
    var movie = Movie.builder()
        .id(event.movieId())
        .title(event.movie().title())
        .price(event.movie().price())
        .build();
    repository.save(movie);
  }

  @EventHandler
  @Transactional
  public void on(MovieUpdatedEvent2 event) {
    var movie = repository.findById(event.movieId()).orElseThrow();
    movie.setTitle(event.movie().title());
  }

  @EventHandler
  public void on(MovieDeletedEvent2 event) {
    repository.deleteById(event.movieId());
  }
}
