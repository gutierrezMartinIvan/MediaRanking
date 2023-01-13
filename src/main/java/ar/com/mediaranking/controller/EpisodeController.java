package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.response.EpisodeResponse;
import ar.com.mediaranking.service.EpisodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/episode")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @GetMapping
    public ResponseEntity<List<EpisodeResponse>> getAllEpisodes(@RequestParam(required = false) Long seriesId,
                                                               @RequestParam(required = false) Long seasonId,
                                                               @RequestParam(required = false) Integer seasonNumber,
                                                               @RequestParam(required = false) Integer episodeNumber,
                                                               @RequestParam(required = false) Integer year,
                                                               @RequestParam(required = false) String title
                                                               ) {
        return ResponseEntity.ok(episodeService.getAll(seriesId, seasonId, seasonNumber, episodeNumber, year, title));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EpisodeResponse> getEpisodeById(@PathVariable Long id) {
        return ResponseEntity.ok(episodeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<EpisodeResponse> createEpisode(@RequestBody EpisodeRequest request) {
        return new ResponseEntity<>(episodeService.save(request), HttpStatus.CREATED);
    }

    @PostMapping("/list")
    public ResponseEntity<List<EpisodeResponse>> createEpisode(@RequestBody List<EpisodeRequest> request) {
        return new ResponseEntity<>(episodeService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<EpisodeResponse> updateEpisode(@PathVariable Long id, @RequestBody EpisodeRequest request) {
        return ResponseEntity.ok(episodeService.update(id, request));
    }

    @DeleteMapping("{id}")
    public void deleteEpisode(@PathVariable Long id) {
        episodeService.delete(id);
    }

}
