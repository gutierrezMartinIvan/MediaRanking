package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.*;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.request.SeasonRequest;
import ar.com.mediaranking.models.request.SeasonUpdate;
import ar.com.mediaranking.models.response.SeasonResponse;
import ar.com.mediaranking.service.SeasonService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

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
    public SeasonResponse update(Long id, SeasonUpdate request) {
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
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Season with ID: " + id +" not found");
        }
    }

    @Override
    public void deleteAll(List<SeasonEntity> seasons) {
        repository.deleteAll(seasons);
    }

    @Override
    public List<SeasonResponse> getAll(Long seriesId, Integer seasonNumber, Integer year, String title) {
        Specification<SeasonEntity> spec = where(null);

        if(title != null){
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if(seasonNumber != null){
            spec = spec.and((root, query, cb) -> cb.equal(root.get("seasonNumber"), seasonNumber));
        }
        if(year != null){
            spec = spec.and((root, query, cb) -> cb.equal(root.get("year"), year));
        }
        if(seriesId != null){
            spec = spec.and((root, query, cb) -> cb.equal(root.get("series").get("id"), seriesId));
        }

        return mapper.convertEntityToDto(repository.findAll(spec));
    }

    @Override
    public SeasonResponse getById(Long id) {
        return mapper.convertEntityToDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Season with ID: " + id +" not found")));
    }

}
