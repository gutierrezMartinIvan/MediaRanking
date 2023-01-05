package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.entity.filter.SeriesFilter;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.specification.SeriesSpecification;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.ReviewResponse;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.service.ISeriesService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean isNull(SeriesRequest request) {
        return false;
    }

    @Override
    public SeriesResponse save(SeriesRequest request) /*throws NameOrContentAreNull*/ {
        SeriesEntity entity = mapper.convertDtoToEntity(request);
        SeriesEntity entitySave = repository.save(entity);
        SeriesResponse response = mapper.convertEntityToDto(entitySave);
        return response;
    }

    @Override
    public List<SeriesResponse> getAll() {
        return mapper.convertSeriesToDto(repository.findAll());
    }

    @Override
    public SeriesResponse getSerieById(Long id) {
        //TODO validate and throw exceptions
        return mapper.convertEntityToDto(repository.getReferenceById(id));
    }

    @Override
    public List<SeriesResponse> getByFilters(String tittle, String author, List<String> genres, Integer year) {
        SeriesFilter seriesFilter = new SeriesFilter(tittle, author, year, genres);
        List<SeriesEntity> entities = repository.findAll(seriesSpecification.getByFilters(seriesFilter));
        List<SeriesResponse> responses = mapper.convertSeriesToDto(entities);
        return responses;
    }

    @Override
    public void deleteSerieById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public SeriesResponse insertReview2Series(Long id, ReviewRequest review) {
        SeriesEntity entityUpdated = null;
        Optional<SeriesEntity> seriesOptional = repository.findById(id);

        if (seriesOptional.isPresent())
            entityUpdated = seriesOptional.get();

        ReviewEntity reviewSaved = reviewService.saveSeries(review, seriesOptional.get());
        entityUpdated.getReviews().add(reviewSaved);
        repository.save(entityUpdated);
        return mapper.convertEntityToDto(entityUpdated);
    }


}
