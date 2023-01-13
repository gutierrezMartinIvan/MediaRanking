package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.response.SeasonResponse;
import ar.com.mediaranking.models.request.SeasonUpdateRequest;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.service.SeasonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/season")
@Schema(
        name = "Season management",
        description = "Here you can use all the provides features for seasons"
)
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
    public ResponseEntity<SeasonResponse> createSeason(@RequestBody SeasonRequest request) {
        return ResponseEntity.ok(seasonService.save(request));
    }

    @Operation(
            summary = "Save a list of new seasons",
            description = "This feature lets register a list of seasons to the system"
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
    public ResponseEntity<List<SeasonResponse>> createSeason(@RequestBody List<SeasonRequest> request) {
        return ResponseEntity.ok(seasonService.save(request));
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
    @PutMapping("{id}")
    public ResponseEntity<SeasonResponse> updateSeason(@PathVariable Long id, @RequestBody SeasonUpdateRequest request) {
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
