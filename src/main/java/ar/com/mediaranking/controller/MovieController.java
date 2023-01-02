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
    public List<MovieEntity> getMovies(@RequestParam(required = false) String title,
                                          @RequestParam(required = false) String director,
                                          @RequestParam(required = false) String genre) {
        return movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public MovieEntity getMovieById(@PathVariable Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @PostMapping
    public MovieEntity createMovie(@RequestBody MovieEntity movie) {
        return movieRepository.save(movie);
    }

    @DeleteMapping
    public void deleteMovie(@RequestParam long id) {
        movieRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public MovieEntity updateMovie(@RequestBody MovieEntity movie) {
        return movieRepository.save(movie);
    }
}
