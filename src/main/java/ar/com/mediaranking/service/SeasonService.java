package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.request.SeasonRequest;

public interface SeasonService {
    SeasonEntity save(SeasonRequest season, SeriesEntity entity);
}
