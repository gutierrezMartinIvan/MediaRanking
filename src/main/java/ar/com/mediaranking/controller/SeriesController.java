package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.service.ISeriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/series")
@Schema(name = "Series Controller", description = "Controller for series endpoints")
public class SeriesController {

    @Autowired
    private ISeriesService seriesService;

    @Autowired
    private IReviewService reviewService;

    @Operation(summary = "Get all series")
    @GetMapping("/getAll")
    public ResponseEntity<List<SeriesResponse>> getAllSeries() {
        return ResponseEntity.ok(seriesService.getAll());
    }

    @Operation(summary = "Get series by filters")
    @GetMapping("/filter")
    public ResponseEntity<List<SeriesResponse>> getSeriesByFilters(@RequestParam(required = false) String title,
                                                                   @RequestParam(required = false) String author,
                                                                   @RequestParam(required = false) Set<String> genres,
                                                                   @RequestParam(required = false) Integer year
    ) {
        List<SeriesResponse> responses = seriesService.getByFilters(title, author, genres, year);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Get a series by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponse> getSeriesById(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.getSerieById(id));
    }

    @Transactional
    @PostMapping("/save")
    //TODO: Add validation
    public ResponseEntity<SeriesResponse> createSeries(@RequestBody SeriesRequest request) {
        return ResponseEntity.ok(seriesService.save(request));
    }

    @Operation(summary = "Delete a series by its ID")
    @DeleteMapping("/delete/{id}")
    public void deleteSerieById(@PathVariable Long id) {
        seriesService.deleteSerieById(id);
    }

    @Operation(summary = "Update a series by its ID")
    @PutMapping("/update/{id}")
    public ResponseEntity<SeriesResponse> updateSeries(@PathVariable Long id, @RequestBody SeriesRequest request) {
        SeriesResponse response = seriesService.update(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Insert a review in a series")
    @PostMapping("/review/{id}")
    public ResponseEntity<SeriesResponse> insertReviewInSeries( @PathVariable Long id, @RequestBody ReviewRequest review) {
        SeriesResponse response = seriesService.insertReview2Series(id, review);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/seasons")
    public ResponseEntity<SeriesResponse> addSeasonsToSeries(@PathVariable Long id, @RequestBody @Valid List<SeasonRequest> seasons) {
        SeriesResponse response = seriesService.addSeasonsToSeries(id, seasons);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/seasons/{seasonId}/episodes")
    public ResponseEntity<SeriesResponse> addEpisodesToSeason(@PathVariable Long seasonId, @RequestBody @Valid List<EpisodeRequest> episodes) {
        SeriesResponse response = seriesService.addEpisodesToSeason(seasonId, episodes);
        return ResponseEntity.ok(response);
    }

}
