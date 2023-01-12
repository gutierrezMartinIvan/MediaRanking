package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.repository.EpisodeRepository;
import ar.com.mediaranking.models.repository.SeasonRepository;
import ar.com.mediaranking.models.request.EpisodeRequest;
import ar.com.mediaranking.models.response.EpisodeResponse;
import ar.com.mediaranking.service.EpisodeService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EpisodeServiceImpl implements EpisodeService {

    @Autowired
    private EpisodeRepository repository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private DtoToEntityConverter mapper;

    @Override
    public EpisodeEntity save(EpisodeEntity episode, SeasonEntity seasonEntity) {
        episode.setSeason(seasonEntity);
        return repository.save(episode);
    }

    @Override
    public EpisodeResponse save(EpisodeRequest request) {
        SeasonEntity seasonEntity = seasonRepository.findById(request.getSeasonId()).orElseThrow(() -> new NotFoundException("Season not found"));
        EpisodeEntity episodeEntity = save(mapper.convertDtoToEntity(request), seasonEntity);
        return mapper.convertEntityToDto(episodeEntity);
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
        repository.deleteById(id);
    }
}
