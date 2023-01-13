package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeasonUpdateRequest;
import ar.com.mediaranking.models.response.SeasonResponse;

import java.util.List;

public interface SeasonService {
    SeasonEntity save(SeasonEntity season, SeriesEntity entity);

    List<SeasonEntity> save(List<SeasonRequest> season, SeriesEntity entity);

    SeasonResponse save(SeasonRequest request);

    List<SeasonResponse> save(List<SeasonRequest> request);

    SeasonResponse update(Long id, SeasonUpdateRequest request);

    void delete(Long id);

    void deleteAll(List<SeasonEntity> seasons);
}
