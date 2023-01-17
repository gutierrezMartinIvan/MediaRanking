package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.request.ReviewUpdate;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.models.response.ReviewResponse;
import ar.com.mediaranking.service.IReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Tag(
        name = "Review management",
        description = "Here you can use all the provides features for reviews"
)
public class ReviewController {
    @Autowired
    private IReviewService reviewService;


    @Operation(
            summary = "Get all reviews",
            description = "In this feature you can look up for all the reviews"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Reviews found successfully!")
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/movie")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@RequestParam Long id, @RequestParam(required = false) @Parameter(schema=@Schema(description="order", type="string", allowableValues= {"asc", "des"})) String order) {
        return ResponseEntity.ok(reviewService.findAllByMovieId(id, order));
    }

    @Operation(
            summary = "Post a review for a movie",
            description = "In this feature you can save a review for a movie"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Review created successfully!"),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/movie")
    public ResponseEntity<ReviewResponse> createReviewForMovie(@Valid @RequestBody ReviewRequest review) {
        return new ResponseEntity<>(reviewService.createReviewForMovie(review), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Post a review for a series",
            description = "In this feature you can save a review for a series"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Review created successfully!"),
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/series")
    public ResponseEntity<ReviewResponse> createReviewForSeries(@Valid @RequestBody ReviewRequest review) {
        return new ResponseEntity<>(reviewService.createReviewForSeries(review), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get reviews by series ID",
            description = "In this feature you can look up for reviews by a series ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Reviews found successfully!"),
                    @ApiResponse(responseCode = "404", description = "Series not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/series")
    public ResponseEntity<List<ReviewResponse>> getReviewsBySeriesId(@RequestParam Long id, @RequestParam(required = false) @Parameter(schema=@Schema(description="order", type="string", allowableValues= {"asc", "des"})) String order) {
        return ResponseEntity.ok(reviewService.findAllBySeriesId(id, order));
    }

    @Operation(
            summary = "Get reviews by user ID",
            description = "In this feature you can look up for reviews by an user ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Reviews found successfully!"),
                    @ApiResponse(responseCode = "404", description = "User ID not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@RequestParam String id) {
        return ResponseEntity.ok(reviewService.findAllByUserId(id));
    }

    @Operation(
            summary = "Update reviews by user ID",
            description = "In this feature you can update a review by its ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Review found successfully!"),
                    @ApiResponse(responseCode = "404", description = "Review not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @SecurityRequirement(name = "Bearer Authentication")
    @Transactional
    @PatchMapping("{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id,@Valid @RequestBody ReviewUpdate reviewRequest) {
        return ResponseEntity.ok(reviewService.update(id, reviewRequest));
    }
}
