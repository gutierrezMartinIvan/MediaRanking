package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.filter.EpisodeFilter;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.repository.specification.EpisodeSpecification;
import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.response.EpisodeResponse;
import ar.com.mediaranking.service.EpisodeService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    private EpisodeRepository repository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private DtoToEntityConverter mapper;

    @Override
    public EpisodeResponse save(EpisodeRequest request) {
        SeasonEntity seasonEntity = seasonRepository.findById(request.getSeasonId()).orElseThrow(() -> new NotFoundException("Season not found"));

        if(seasonEntity.getEpisodes().stream().anyMatch(episode -> episode.getEpisodeNumber().equals(request.getEpisodeNumber()))) {
            throw new AlreadyExistsException("Episode already exists with that number");
        }
        EpisodeEntity episode = mapper.convertDtoToEntity(request);
        episode.setSeason(seasonEntity);

        return mapper.convertEntityToDto(repository.save(episode));
    }

    @Override
    public List<EpisodeResponse> save(List<EpisodeRequest> request) {
        List<EpisodeResponse> response = new ArrayList<>();

        for (EpisodeRequest episodeRequest : request) {
            response.add(save(episodeRequest));
        }
        return response;
    }

    @Override
    public EpisodeResponse update(Long id, EpisodeRequest request) {
        EpisodeEntity episodeEntity = repository.findById(id).orElseThrow(() -> new NotFoundException("Episode with id " + id + " not found"));

        if(request.getTitle() != null) {
            episodeEntity.setTitle(request.getTitle());
        }
        if(request.getEpisodeNumber() != null) {
            episodeEntity.setEpisodeNumber(request.getEpisodeNumber());
        }
        if(request.getSeasonId() != null) {
            SeasonEntity seasonEntity = seasonRepository.findById(request.getSeasonId()).orElseThrow(() -> new NotFoundException("Season with id " + request.getSeasonId() + " not found"));

            episodeEntity.setSeason(seasonEntity);
            seasonEntity.getEpisodes().add(episodeEntity);
            seasonRepository.save(seasonEntity);
        }
        if(request.getDescription() != null) {
            episodeEntity.setDescription(request.getDescription());
        }

        return mapper.convertEntityToDto(repository.save(episodeEntity));
    }

    @Override
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new NotFoundException("Episode with id " + id + " not found");
        }
    }

    @Override
    public List<EpisodeResponse> getAll(Long seriesId, Long seasonId, Integer seasonNumber, Integer episodeNumber, Integer year, String title) {
        EpisodeFilter filter = EpisodeFilter.builder().title(title).episodeNumber(episodeNumber).seasonNumber(seasonNumber).year(year).seasonId(seasonId).seriesId(seriesId).build();
        Specification<EpisodeEntity> spec = EpisodeSpecification.getByFilters(filter);

        return mapper.convertEpisodesToDto(repository.findAll(spec));
    }

    @Override
    public EpisodeResponse getById(Long id) {
        EpisodeEntity episode = repository.findById(id).orElseThrow(() -> new NotFoundException("Episode with id " + id + " not found"));
        return mapper.convertEntityToDto(episode);
    }
}
