package ar.com.mediaranking.service;

import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;

import java.util.List;

public interface ISeriesService {

    boolean isNull(SeriesRequest request);

    SeriesResponse save(SeriesRequest request);

    List<SeriesResponse> getAll();
}
