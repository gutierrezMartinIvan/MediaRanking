package ar.com.mediaranking.utils;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.SeriesRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.models.response.SeriesResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoToEntityConverter {

    @Autowired
    private ModelMapper modelMapper;

    public SeriesEntity convertDtoToEntity(SeriesRequest request) {
        return modelMapper.map(request, SeriesEntity.class);
    }

    public SeriesResponse convertEntityToDto(SeriesEntity entity) {
        return modelMapper.map(entity, SeriesResponse.class);
    }


    public List<SeriesResponse> convertSeriesToDto(List<SeriesEntity> series) {
        return series.stream()
                .map(this::convertEntityToDto)
               .collect(Collectors.toList());
    }

    public MovieEntity convertDtoToEntity(MovieRequest request) {
        return modelMapper.map(request, MovieEntity.class);
    }

    public MovieResponse convertEntityToDto(MovieEntity entity) {
        return modelMapper.map(entity, MovieResponse.class);
    }

    public List<MovieResponse> convertMoviesToDto(List<MovieEntity> entities) {
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

}
