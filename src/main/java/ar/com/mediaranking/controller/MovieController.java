package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService service;


    @GetMapping
    public ResponseEntity<List<MovieResponse>> getMovies(@RequestParam(required = false) String title,
                                                    @RequestParam(required = false) String director,
                                                    @RequestParam(required = false) String genre) {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MovieResponse>> getMoviesFilter(@RequestParam(required = false) String title,
                                                         @RequestParam(required = false) String director,
                                                         @RequestParam(required = false) Set<String> genres) {
        List<MovieResponse> movie = service.findByFilter(title, director, genres);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok( service.findById(id) );
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest movie) {
        return ResponseEntity.ok(service.save(movie));
    }

    @DeleteMapping
    public void deleteMovie(@RequestParam long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable long id, @RequestBody MovieRequest movie) {
        return ResponseEntity.ok(service.update(id, movie));
    }
}
