package ar.com.mediaranking.controller;


import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.service.ISeriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @GetMapping()
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
    @PostMapping()
    //TODO: Add validation
    public ResponseEntity<SeriesResponse> createSeries(@RequestBody SeriesRequest request) {
        return new ResponseEntity<>(seriesService.save(request), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a series by its ID")
    @DeleteMapping("{id}")
    public void deleteSerieById(@PathVariable Long id) {
        seriesService.deleteSerieById(id);
    }

    @Operation(summary = "Update a series by its ID")
    @PutMapping("{id}")
    public ResponseEntity<SeriesResponse> updateSeries(@PathVariable Long id, @RequestBody SeriesRequest request) {
        SeriesResponse response = seriesService.update(id, request);
        return ResponseEntity.ok(response);
    }

}
