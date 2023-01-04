package ar.com.mediaranking.service;

import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;

import java.util.List;

public interface ISeriesService {

    boolean isNull(SeriesRequest request);

    SeriesResponse save(SeriesRequest request);

    List<SeriesResponse> getAll();

    SeriesResponse getSerieById(Long id);

    List<SeriesResponse> getByFilters(String tittle, String author, List<String> genres, Integer year);

    void deleteSerieById(Long id);

    SeriesResponse insertReview2Series(Long id, ReviewRequest review);
}
