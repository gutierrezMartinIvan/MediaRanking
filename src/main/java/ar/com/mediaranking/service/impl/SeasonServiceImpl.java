package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.service.EpisodeService;
import ar.com.mediaranking.service.SeasonService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository repository;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private DtoToEntityConverter mapper;

    @Override
    public SeasonEntity save(SeasonEntity season, SeriesEntity entity) {
        season.setSeries(entity);

       SeasonEntity savedSeason = repository.save(season);
       for(EpisodeEntity episode : savedSeason.getEpisodes()) {
           episode.setSeason(savedSeason);
           episodeRepository.save(episode);
       }

        return  savedSeason;
    }
}
