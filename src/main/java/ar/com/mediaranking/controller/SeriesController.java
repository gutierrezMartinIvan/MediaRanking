package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.models.request.SeriesUpdate;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.service.ISeriesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
@RequestMapping("/series")
@Tag(
        name = "Series Controller",
        description = "Controller for series endpoints"
)
public class SeriesController {
    @Autowired
    private ISeriesService seriesService;

    @Operation(
            summary = "Get all series",
            description = "This feature lets all get all series.")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Series found successfully!")
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping()
    public ResponseEntity<List<SeriesResponse>> getAllSeries() {
        return ResponseEntity.ok(seriesService.getAll());
    }

    @Operation(
            summary = "Get series by filters",
            description = "In this feature you can look up for a series by its tittle, author, genres or year")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Series found successfully!")
            })
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/filter")
    public ResponseEntity<List<SeriesResponse>> getSeriesByFilters(@RequestParam(required = false) String title,
                                                                   @RequestParam(required = false) String author,
                                                                   @RequestParam(required = false) Set<String> genres,
                                                                   @RequestParam(required = false) Integer year
    ) {
        return ResponseEntity.ok(seriesService.getByFilters(title, author, genres, year));
    }

    @Operation(
            summary = "Get a series by its ID",
            description = "In this feature you can look up for a series by its ID")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Series found successfully!"),
                    @ApiResponse(responseCode = "404", description = "Series not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/{id}")
    public ResponseEntity<SeriesResponse> getSeriesById(@PathVariable Long id) {
        return ResponseEntity.ok(seriesService.getSerieById(id));
    }

    @Operation(
            summary = "Create a new series",
            description = "In this feature you can save for a series, the season and the episodes list are optional"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Series save successfully!"),
                    @ApiResponse(responseCode = "409", description = "Series is already registered!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @Transactional
    @PostMapping()
    public ResponseEntity<SeriesResponse> createSeries(@Valid @RequestBody SeriesRequest request) {
        return new ResponseEntity<>(seriesService.save(request), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Delete series by its ID",
            description = "In this feature you can delete for a series by its ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Series deleted successfully!"),
                    @ApiResponse(responseCode = "404", description = "Series not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("{id}")
    public void deleteSeriesById(@PathVariable Long id) {
        seriesService.deleteSerieById(id);
    }

    @Operation(
            summary = "Update series by its ID",
            description = "In this feature you can update for a series by its ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Series updated successfully!"),
                    @ApiResponse(responseCode = "404", description = "Series not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @Transactional
    @PatchMapping("{id}")
    public ResponseEntity<SeriesResponse> updateSeries(@PathVariable Long id,@Valid @RequestBody SeriesUpdate request) {
        SeriesResponse response = seriesService.update(id, request);
        return ResponseEntity.ok(response);
    }

}
