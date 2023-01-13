package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeasonUpdateRequest;
import ar.com.mediaranking.models.response.SeasonResponse;
import ar.com.mediaranking.service.SeasonService;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/season")
@Schema(name = "Season Controller", description = "Controller for series endpoints")
public class SeasonController {
    @Autowired
    private SeasonService seasonService;

    @GetMapping
    public ResponseEntity<List<SeasonResponse>> getAllSeasons(@RequestParam(required = false) Long seriesId,
                                                              @RequestParam(required = false) Integer seasonNumber,
                                                              @RequestParam(required = false) Integer year,
                                                              @RequestParam(required = false) String title
                                                              ) {
        return ResponseEntity.ok(seasonService.getAll(seriesId, seasonNumber, year, title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeasonResponse> getSeasonById(@PathVariable Long id) {
        return ResponseEntity.ok(seasonService.getById(id));
    }


    @PostMapping
    public ResponseEntity<SeasonResponse> createSeason(@RequestBody SeasonRequest request) {
        return ResponseEntity.ok(seasonService.save(request));
    }

    @PostMapping("/list")
    public ResponseEntity<List<SeasonResponse>> createSeason(@RequestBody List<SeasonRequest> request) {
        return ResponseEntity.ok(seasonService.save(request));
    }

    @PutMapping("{id}")
    public ResponseEntity<SeasonResponse> updateSeason(@PathVariable Long id, @RequestBody SeasonUpdateRequest request) {
        return ResponseEntity.ok(seasonService.update(id, request));
    }

    @DeleteMapping("{id}")
    public void deleteSeason(@PathVariable Long id) {
        seasonService.delete(id);
    }

}
