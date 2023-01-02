package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<MovieEntity> getAllMovies() {
        return movieRepository.findAll();
    }

    /*@GetMapping
    public MovieEntity getMovieById(@RequestParam long id, @RequestParam String title, @RequestParam String description, @RequestParam String director, @RequestParam List<String> genres, @RequestParam int duration, @RequestParam ReviewEntity review) {
        return movieRepository.findById(1L).orElse(null);
    }*/

    @PostMapping
    public MovieEntity createMovie(@RequestBody MovieEntity movie) {
        return movieRepository.save(movie);
    }
}
