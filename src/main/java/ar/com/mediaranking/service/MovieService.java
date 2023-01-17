package ar.com.mediaranking.service;

import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.MovieUpdate;
import ar.com.mediaranking.models.response.MovieResponse;

import java.util.List;
import java.util.Set;

public interface MovieService {

    MovieResponse save(MovieRequest request) /*throws NameOrContentAreNull*/;

    List<MovieResponse> findAll();

    void deleteById(long id);

    MovieResponse findById(Long id);

    MovieResponse update(long id, MovieUpdate movie);

    List<MovieResponse> findByFilter(String title, String director,Integer year, Integer minDuration, Integer maxDuration, Set<String> genres);

    List<MovieResponse> saveList(List<MovieRequest> movieList);
}
