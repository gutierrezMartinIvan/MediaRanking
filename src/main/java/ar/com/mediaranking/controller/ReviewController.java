package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.ReviewResponse;
import ar.com.mediaranking.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;


    @GetMapping("/movie")
    public ResponseEntity<List<ReviewResponse>> getReviewsByMovieId(@RequestParam Long id, @RequestParam(required = false) String order) {
        return ResponseEntity.ok(reviewService.findAllByMovieId(id, order));
    }

    @GetMapping("/series")
    public ResponseEntity<List<ReviewResponse>> getReviewsBySeriesId(@RequestParam Long id, @RequestParam(required = false) String order) {
        return ResponseEntity.ok(reviewService.findAllBySeriesId(id, order));
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReviewResponse>> getReviewsByUserId(@RequestParam String id) {
        return ResponseEntity.ok(reviewService.findAllByUserId(id));
    }

    @PutMapping("{id}")
    public ResponseEntity<ReviewResponse> updateReview(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        // TODO: reviewUpdate body?
        return ResponseEntity.ok(reviewService.update(id, reviewRequest));
    }
}
