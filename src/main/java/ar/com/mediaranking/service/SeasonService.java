package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;

public interface SeasonService {
    SeasonEntity save(SeasonEntity season, SeriesEntity entity);
}
