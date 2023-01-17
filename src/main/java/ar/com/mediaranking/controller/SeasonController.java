package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.response.SeasonResponse;
import ar.com.mediaranking.models.request.SeasonUpdate;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.service.SeasonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/season")
@Tag(
        name = "Season management",
        description = "Here you can use all the provides features for seasons"
)
public class SeasonController {
    @Autowired
    private SeasonService seasonService;

    @Operation(
            summary = "Get seasons by filters",
            description = "In this feature you can look up for a season by its series ID, title, year and season number"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Seasons found successfully!")
            }
    )
    @GetMapping
    public ResponseEntity<List<SeasonResponse>> getAllSeasons(@RequestParam(required = false) Long seriesId,
                                                              @RequestParam(required = false) Integer seasonNumber,
                                                              @RequestParam(required = false) Integer year,
                                                              @RequestParam(required = false) String title
                                                              ) {
        return ResponseEntity.ok(seasonService.getAll(seriesId, seasonNumber, year, title));
    }

    @Operation(
            summary = "Get season by its ID",
            description = "In this feature you can look up for a season by its ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Season found successfully!"),
                    @ApiResponse(responseCode = "404", description = "Season not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<SeasonResponse> getSeasonById(@PathVariable Long id) {
        return ResponseEntity.ok(seasonService.getById(id));
    }

    @Operation(
            summary = "Creates a new season",
            description = "This feature lets register a new season to the system"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Season saved correctly!"),
                    @ApiResponse(responseCode = "409", description = "Season is already registered!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PostMapping
    public ResponseEntity<SeasonResponse> createSeason(@Valid @RequestBody SeasonRequest request) {
        return ResponseEntity.created(URI.create("/review")).body(seasonService.save(request));
    }

    @Operation(
            summary = "Save a list of new seasons",
            description = "This feature lets register a list of seasons to the system, episodes list is optional"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Seasons list saved correctly!"),
                    @ApiResponse(responseCode = "409", description = "Some of the seasons are already registered!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PostMapping("/list")
    public ResponseEntity<List<SeasonResponse>> createSeason(@Valid @RequestBody List<SeasonRequest> request) {
        return ResponseEntity.created(URI.create("/review")).body(seasonService.save(request));
    }

    @Operation(
            summary = "Update a season",
            description = "This feature lets update a season in the system"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Season updated correctly!"),
                    @ApiResponse(responseCode = "404", description = "Season not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @PatchMapping("{id}")
    public ResponseEntity<SeasonResponse> updateSeason(@PathVariable Long id, @Valid @RequestBody SeasonUpdate request) {
        return ResponseEntity.ok(seasonService.update(id, request));
    }

    @Operation(
            summary = "delete a season",
            description = "This feature lets delete a season in the system"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Season delete correctly!"),
                    @ApiResponse(responseCode = "404", description = "Season not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @Transactional
    @DeleteMapping("{id}")
    public void deleteSeason(@PathVariable Long id) {
        seasonService.delete(id);
    }

}
