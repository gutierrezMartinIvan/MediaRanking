package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.models.response.ReviewResponse;
import ar.com.mediaranking.service.IReviewService;
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
@RequestMapping("/reviews")
@Schema(
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
    @GetMapping("/movie")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@RequestParam Long id, @RequestParam(required = false) String order) {
        return ResponseEntity.ok(reviewService.findAllByMovieId(id, order));
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
    @GetMapping("/series")
    public ResponseEntity<List<ReviewResponse>> getReviewsBySeriesId(@RequestParam Long id, @RequestParam(required = false) String order) {
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
    @Transactional
    @PutMapping("{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        return ResponseEntity.ok(reviewService.update(id, reviewRequest));
    }
}
