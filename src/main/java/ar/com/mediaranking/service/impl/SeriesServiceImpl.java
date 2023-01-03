package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.entity.filter.SeriesFilter;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.specification.SeriesSpecification;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.ISeriesService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesServiceImpl implements ISeriesService {

    @Autowired
    private ISeriesRepository repository;

    @Autowired
    private DtoToEntityConverter mapper;

    @Autowired
    private SeriesSpecification seriesSpecification;

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
    public List<SeriesResponse> getByFilters(String tittle, String author, List<String> genres, Integer year) {
        SeriesFilter seriesFilter = new SeriesFilter(tittle, author, year, genres);
        List<SeriesEntity> entities = repository.findAll(seriesSpecification.getByFilters(seriesFilter));
        List<SeriesResponse> responses = mapper.convertSeriesToDto(entities);
        return responses;
    }


}
