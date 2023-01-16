package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/movies")
@Tag(
        name = "Movie management",
        description = "Here you can use all the provides features for movies"
)
public class MovieController {

    @Autowired
    private MovieService service;

    @Operation(
            summary = "Get movies",
            description = "This feature lets all get all movies information."
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Movies found successfully!")
            })
    @GetMapping
    public ResponseEntity<List<MovieResponse>> getMovies() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(
            summary = "Get movies by filters",
            description = "In this feature you can look up for a movie by its tittle, director, genres, year or the max or min duration"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Movies found successfully!")
            }
    )
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

    @Operation(
            summary = "Get movie by its ID",
            description = "In this feature you can look up for a movie by its ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Movie found successfully!"),
                    @ApiResponse(responseCode = "404", description = "Movie not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(
            summary = "Save movie",
            description = "In this feature you can save a movie"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Movie save successfully!"),
                    @ApiResponse(responseCode = "409", description = "Movie is already registered!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PostMapping
    public ResponseEntity<MovieResponse> createMovie(@Valid @RequestBody MovieRequest movie) {
        return new ResponseEntity<>(service.save(movie), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Save a list of movies",
            description = "In this feature you can save a list of movies"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Movie save successfully!"),
                    @ApiResponse(responseCode = "409", description = "Movie is already registered!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping("/list")
    public ResponseEntity<List<MovieResponse>> createMovieList(@Valid @RequestBody List<MovieRequest> movieList) {
        return ResponseEntity.ok(service.saveList(movieList));
    }

    @Operation(
            summary = "Delete movie by its ID",
            description = "In this feature you can delete for a movie by its ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Movie deleted successfully!"),
                    @ApiResponse(responseCode = "404", description = "Movie not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @DeleteMapping
    public void deleteMovie(@RequestParam long id) {
        service.deleteById(id);
    }

    @Operation(
            summary = "Update movie by its ID",
            description = "In this feature you can update for a movie by its ID, you can do a partial update"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Movie updated successfully!"),
                    @ApiResponse(responseCode = "404", description = "Movie not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> updateMovie(@PathVariable long id, @RequestBody MovieRequest movie) {
        // TODO: MovieUpdateRequest ?
        return ResponseEntity.ok(service.update(id, movie));
    }
}
