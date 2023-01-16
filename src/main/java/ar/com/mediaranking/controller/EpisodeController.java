package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.models.response.EpisodeResponse;
import ar.com.mediaranking.service.EpisodeService;
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

@RestController
@RequestMapping("/episode")
@Tag(
        name = "Episode management",
        description = "Here you can use all the provides features for episodes"
)
public class EpisodeController {
    @Autowired
    private EpisodeService episodeService;

    @Operation(
            summary = "Get episodes by filters",
            description = "In this feature you can look up for an episode by its series ID, season ID, title, year, season number or episode number"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Episodes found successfully!")
            }
    )
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

    @Operation(
            summary = "Get episode by its ID",
            description = "In this feature you can look up for an episode by its ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Episode found successfully!"),
                    @ApiResponse(responseCode = "404", description = "Episode not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<EpisodeResponse> getEpisodeById(@PathVariable Long id) {
        return ResponseEntity.ok(episodeService.getById(id));
    }

    @Operation(
            summary = "Create a new episode",
            description = "This feature lets register a new episode to the system"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Episode saved correctly!"),
                    @ApiResponse(responseCode = "409", description = "Episode is already registered!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PostMapping
    public ResponseEntity<EpisodeResponse> createEpisode(@RequestBody EpisodeRequest request) {
        return new ResponseEntity<>(episodeService.save(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Save a list of new episodes",
            description = "This feature lets register a list of episodes to the system"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Episodes list saved correctly!"),
                    @ApiResponse(responseCode = "409", description = "Some of the episodes are already registered!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PostMapping("/save/list")
    public ResponseEntity<List<EpisodeResponse>> createEpisode(@Valid @RequestBody List<EpisodeRequest> request) {
        return new ResponseEntity<>(episodeService.save(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update an episode",
            description = "This feature lets update an episode in the system"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Episode updated correctly!"),
                    @ApiResponse(responseCode = "404", description = "Episode not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PatchMapping("/{id}")
    public ResponseEntity<EpisodeResponse> updateEpisode(@PathVariable Long id, @RequestBody EpisodeRequest request) {
        return ResponseEntity.ok(episodeService.update(id, request));
    }

    @Operation(
            summary = "delete an episode",
            description = "This feature lets delete an episode in the system partially"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Episode delete correctly!"),
                    @ApiResponse(responseCode = "404", description = "Episode not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @DeleteMapping("/{id}")
    public void deleteEpisode(@PathVariable Long id) {
        episodeService.delete(id);
    }

}
