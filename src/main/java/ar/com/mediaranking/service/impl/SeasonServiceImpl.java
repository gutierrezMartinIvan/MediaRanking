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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private SeasonRepository repository;

    @Autowired
    private EpisodeService episodeService;

    @Autowired
    private DtoToEntityConverter mapper;

    @Override
    public SeasonEntity save(SeasonRequest season, SeriesEntity entity) {
        SeasonEntity seasonEntity = mapper.convertDtoToEntity(season);
        seasonEntity.setSeries(entity);

        SeasonEntity saveSeason = repository.save(seasonEntity);


        for(EpisodeRequest episode : season.getEpisodes()) {
            saveSeason.getEpisodes().add(episodeService.save(mapper.convertDtoToEntity(episode),seasonEntity));
        }

        return saveSeason;
    }
}
