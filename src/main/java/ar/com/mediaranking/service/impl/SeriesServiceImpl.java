package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.entity.filter.SeriesFilter;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.specification.SeriesSpecification;
import ar.com.mediaranking.models.request.*;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.service.ISeriesService;
import ar.com.mediaranking.service.SeasonService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class SeriesServiceImpl implements ISeriesService {

    @Autowired
    private ISeriesRepository repository;

    @Autowired
    private DtoToEntityConverter mapper;

    @Autowired
    private SeriesSpecification seriesSpecification;

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private SeasonService seasonService;

    @Override
    public boolean isNull(SeriesRequest request) {
        return false;
    }

    @Override
    public SeriesResponse save(SeriesRequest request) {
        repository.findByTitleAndYear(request.getTitle(), request.getYear()).ifPresent(seriesEntity -> {
                    throw new AlreadyExistsException(
                            "There is already a series with the name: " + request.getTitle() +
                                    " and year: " + request.getYear() +
                                    " with id: " + seriesEntity.getId()
                    );
                });

        SeriesEntity entitySave = repository.save(mapper.convertDtoToEntity(request));
        if(request.getSeasons() != null) {
            for (SeasonEntity season : entitySave.getSeasons())
                seasonService.save(season, entitySave);
        }
        return mapper.convertEntityToDto(repository.save(entitySave));
    }

    @Override
    public List<SeriesResponse> getAll() {
        return mapper.convertSeriesToDto(repository.findAll());
    }

    @Override
    public SeriesResponse getSerieById(Long id) {
        return mapper.convertEntityToDto(repository.findById(id).
                orElseThrow(() -> new NotFoundException("There is not a series with the id: " + id)));
    }

    @Override
    public List<SeriesResponse> getByFilters(String tittle, String author, Set<String> genres, Integer year) {
        SeriesFilter seriesFilter = new SeriesFilter(tittle, author, year, genres);
        List<SeriesEntity> entities = repository.findAll(seriesSpecification.getByFilters(seriesFilter));
        List<SeriesResponse> responses = mapper.convertSeriesToDto(entities);
        return responses;
    }

    @Override
    public void deleteSerieById(Long id) {
        Optional<SeriesEntity> seriesOptional = repository.findById(id);
        if (!seriesOptional.isPresent())
            throw new NotFoundException("There is not a series with the id: " + id);
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public SeriesResponse update(Long id, SeriesUpdate request) {
        SeriesEntity entity = repository.findById(id).orElseThrow(
                () -> new NotFoundException("There is not a series with the id: " + id));

        if(request.getTitle() != null && !request.getTitle().isBlank())
            entity.setTitle(request.getTitle());

        if(request.getAuthor() != null && !request.getAuthor().isBlank())
            entity.setAuthor(request.getAuthor());

        if(request.getGenres() != null && !request.getGenres().isEmpty())
            entity.setGenres(mapper.convertSetStringToGenre(request.getGenres()));

        if(request.getYear() != null && request.getYear() > 0)
            entity.setYear(request.getYear());

        SeriesEntity updatedEntity = repository.save(entity);

        return mapper.convertEntityToDto(updatedEntity);
    }


}
