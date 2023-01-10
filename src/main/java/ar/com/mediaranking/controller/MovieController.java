package ar.com.mediaranking.controller;


import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.service.IReviewService;
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

    @Autowired
    private IReviewService reviewService;


    @GetMapping
    public ResponseEntity<List<MovieResponse>> getMovies() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<MovieResponse>> getMoviesFilter(@RequestParam(required = false) String title,
                                                               @RequestParam(required = false) String director,
                                                               @RequestParam(required = false) Set<String> genres,
                                                               @RequestParam(required = false) Integer year,
                                                               @RequestParam(required = false) Integer minDuration,
                                                               @RequestParam(required = false) Integer maxDuration
    ){
        List<MovieResponse> movie = service.findByFilter(title, director, year, minDuration, maxDuration, genres);
        return ResponseEntity.ok(movie);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id, @RequestParam(required = false) String orderReview) {
        // TODO Check order if ASC or DES
        return ResponseEntity.ok( service.findById(id, orderReview) );
    }

    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@RequestBody MovieRequest movie) {
        return ResponseEntity.ok(service.save(movie));
    }

    @PostMapping("/list")
    public ResponseEntity<List<MovieResponse>> createMovieList(@RequestBody List<MovieRequest> movieList) {
        return ResponseEntity.ok(service.saveList(movieList));
    }

    @DeleteMapping
    public void deleteMovie(@RequestParam long id) {
        service.deleteById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable long id, @RequestBody MovieRequest movie) {
        return ResponseEntity.ok(service.update(id, movie));
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<MovieResponse> addReview(@PathVariable long id, @RequestBody ReviewRequest review) {
        return ResponseEntity.ok(service.insertReview2Movie(id, review));
    }
}
