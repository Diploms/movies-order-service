package ua.karazin.moviesorderservice.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
  private final MovieRepository movieRepository;

  public Optional<Movie> findById(String id) {
    return movieRepository.findById(id);
  }
}
