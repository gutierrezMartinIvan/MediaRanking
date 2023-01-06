package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface MovieService {
    boolean isNull(MovieRequest request);

    MovieResponse save(MovieRequest request) /*throws NameOrContentAreNull*/;

    List<MovieResponse> findAll();

    void deleteById(long id);

    MovieResponse findById(Long id);

    MovieResponse update(long id, MovieRequest movie);

    List<MovieResponse> findByGenre(String genre);

    List<MovieResponse> findByFilter(String title, String director, Set<String> genres);

    MovieResponse addReview(long id, ReviewRequest review);
}
