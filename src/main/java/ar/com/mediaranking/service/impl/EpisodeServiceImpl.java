package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.service.EpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    private EpisodeRepository repository;

    @Override
    public EpisodeEntity save(EpisodeEntity episode, SeasonEntity seasonEntity) {
        episode.setSeason(seasonEntity);
        return repository.save(episode);
    }
}
