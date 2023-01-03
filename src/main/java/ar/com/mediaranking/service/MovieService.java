package ar.com.mediaranking.service;

import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    boolean isNull(MovieRequest request);

    MovieResponse save(MovieRequest request) /*throws NameOrContentAreNull*/;

    List<MovieResponse> findAll(String title, String director, String genre);;

    void deleteById(long id);

    MovieResponse findById(Long id);

    MovieResponse update(long id, MovieRequest movie);

}
