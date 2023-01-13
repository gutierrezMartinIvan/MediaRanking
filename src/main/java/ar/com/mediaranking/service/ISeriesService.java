package ar.com.mediaranking.service;

import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;

import java.util.List;
import java.util.Set;

public interface ISeriesService {

    boolean isNull(SeriesRequest request);

    SeriesResponse save(SeriesRequest request);

    List<SeriesResponse> getAll();

    SeriesResponse getSerieById(Long id);

    List<SeriesResponse> getByFilters(String tittle, String author, Set<String> genres, Integer year);

    void deleteSerieById(Long id);

    SeriesResponse insertReview2Series(Long id, ReviewRequest review);

    SeriesResponse update(Long id, SeriesRequest request);
}
