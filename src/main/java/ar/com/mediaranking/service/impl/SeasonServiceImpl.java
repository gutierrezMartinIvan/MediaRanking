package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.service.EpisodeService;
import ar.com.mediaranking.service.SeasonService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository repository;
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

    @Override
    public List<SeasonEntity> save(List<SeasonRequest> season, SeriesEntity series) {
        List<SeasonEntity> seasons = new ArrayList<>();
        for (SeasonRequest seasonRequest : season) {
            seasons.add(save(mapper.convertDtoToEntity(seasonRequest), series));
        }
        return seasons;
    }

    @Override
    public SeasonEntity addEpisodesToSeason(Long seasonId, List<EpisodeRequest> episodes) {
        SeasonEntity season = repository.findById(seasonId).orElseThrow(() -> new RuntimeException("Season not found"));

        for(EpisodeRequest episodeRequest : episodes) {
            EpisodeEntity episode = mapper.convertDtoToEntity(episodeRequest);
            episode.setSeason(season);
            season.getEpisodes().add(episodeRepository.save(episode));
        }
        return repository.save(season);
    }


}
