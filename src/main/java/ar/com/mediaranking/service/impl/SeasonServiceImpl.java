package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.request.EpisodeSeasonRequest;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeasonUpdateRequest;
import ar.com.mediaranking.models.response.SeasonResponse;
import ar.com.mediaranking.service.SeasonService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeasonServiceImpl implements SeasonService {

    @Autowired
    private ISeriesRepository seriesRepository;

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
       if(season.getEpisodes() != null) {
           for (EpisodeEntity episode : season.getEpisodes()) {
               episode.setSeason(savedSeason);
               episodeRepository.save(episode);
           }
       }
       return savedSeason;
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
    public SeasonResponse save(SeasonRequest request) {
        SeriesEntity serie = seriesRepository.findById(request.getSeriesId()).orElseThrow(
                () -> new NotFoundException("No se encontro la serie con id: " + request.getSeriesId())
        );

        SeasonEntity season = save(mapper.convertDtoToEntity(request), serie);
        return mapper.convertEntityToDto(season);
    }

    @Override
    public List<SeasonResponse> save(List<SeasonRequest> request) {
        List<SeasonResponse> seasons = new ArrayList<>();
        for (SeasonRequest seasonRequest : request) {
            seasons.add(save(seasonRequest));
        }
        return seasons;
    }

    @Override
    public SeasonResponse update(Long id, SeasonUpdateRequest request) {
        SeasonEntity season = repository.findById(id).orElseThrow(() -> new NotFoundException("Season with ID: " + id +" not found"));

        if(request.getSeriesId() != null) {
            season.setSeries(seriesRepository.findById(request.getSeriesId()).orElseThrow(
                () -> new NotFoundException("Series with ID: " + request.getSeriesId() +" not found")
            ));
        }
        if(request.getTitle() != null) season.setTitle(request.getTitle());
        if(request.getSeasonNumber() != null) season.setSeasonNumber(request.getSeasonNumber());
        if(request.getDescription() != null) season.setDescription(request.getDescription());

        SeasonEntity savedSeason = repository.save(season);

        return mapper.convertEntityToDto(savedSeason);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void deleteAll(List<SeasonEntity> seasons) {
        repository.deleteAll(seasons);
    }


}
