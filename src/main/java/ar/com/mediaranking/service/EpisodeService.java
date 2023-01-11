package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;

public interface EpisodeService {
    EpisodeEntity save(EpisodeEntity epsidode, SeasonEntity seasonEntity);
}
