package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.request.SeasonRequest;

import java.util.List;

public interface SeasonService {
    SeasonEntity save(SeasonEntity season, SeriesEntity entity);

    List<SeasonEntity> save(List<SeasonRequest> season, SeriesEntity entity);

    SeasonEntity addEpisodesToSeason(Long seasonId, List<EpisodeRequest> episodes);
}
