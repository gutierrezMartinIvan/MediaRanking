package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.ISeriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/series")
@Schema(name = "Series Controller", description = "Controller for series endpoints")
public class SeriesController {

    @Autowired
    private ISeriesService service;

    @Transactional
    @Operation(summary = "Creates a new series")
    @PostMapping
    public ResponseEntity<SeriesResponse> createSeries(@RequestBody SeriesRequest request) {
        SeriesResponse response = new SeriesResponse();

        if (service.isNull(request))
            return ResponseEntity.badRequest().body(response);

        response = service.save(request);

        return ResponseEntity.created(null).body(response);
    }

    @Operation(summary = "Get all series")
    @GetMapping
    public ResponseEntity<List<SeriesResponse>> getAllSeries() {
        return ResponseEntity.ok(service.getAll());
    }

    @Operation(summary = "Get a serie byy its ID")
    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponse> getSerieById(@RequestParam Long id) {
        return ResponseEntity.ok(service.getSerieById(id));
    }

    @Operation(summary = "Get a serie by filters")
    @GetMapping("/filter")
    public ResponseEntity<List<SeriesResponse>> getDetailsByFilters(
            @RequestParam(required = false) String tittle,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) List<String> genres
    ) {
        List<SeriesResponse> series = service.getByFilters(tittle, author, genres, year);
        return ResponseEntity.ok(series);
    }
}
