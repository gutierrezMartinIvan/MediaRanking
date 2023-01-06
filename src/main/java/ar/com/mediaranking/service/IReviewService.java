package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.ReviewResponse;

public interface IReviewService {
    ReviewEntity save(ReviewRequest reviewRequest);

    ReviewEntity saveSeries(ReviewRequest review, SeriesEntity series);

    ReviewEntity saveMovie(ReviewRequest review, MovieEntity movieEntity);
}
