package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.ReviewResponse;

import java.util.List;

public interface IReviewService {

    List<ReviewResponse> findAllByMovieId(Long id, String order);


    List<ReviewResponse>  findAllBySeriesId(Long id, String order);

    List<ReviewResponse>  findAllByUserId(String id);

    ReviewResponse update(Long id, ReviewRequest reviewRequest);

    ReviewResponse createReviewForMovie(ReviewRequest review);

    ReviewResponse createReviewForSeries(ReviewRequest review);
}
