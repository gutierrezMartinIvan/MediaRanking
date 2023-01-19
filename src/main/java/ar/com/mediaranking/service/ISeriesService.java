package ar.com.mediaranking.service;

import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.request.SeriesUpdate;
import ar.com.mediaranking.models.response.SeriesResponse;

import java.util.List;
import java.util.Set;

public interface ISeriesService {

    SeriesResponse save(SeriesRequest request);

    SeriesResponse getSerieById(Long id);

    List<SeriesResponse> getAll(String tittle, String author, Set<String> genres, Integer year);

    void deleteSerieById(Long id);

    SeriesResponse update(Long id, SeriesUpdate request);
}
