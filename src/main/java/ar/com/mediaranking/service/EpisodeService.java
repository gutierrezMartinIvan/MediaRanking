package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.response.EpisodeResponse;


import java.util.List;

public interface EpisodeService {

    EpisodeResponse save(EpisodeRequest request);

    List<EpisodeResponse> save(List<EpisodeRequest> request);

    EpisodeResponse update(Long id, EpisodeRequest request);

    void delete(Long id);

    List<EpisodeResponse> getAll(Long seriesId, Long seasonId, Integer seasonNumber, Integer episodeNumber, Integer year, String title);

    EpisodeResponse getById(Long id);
}
