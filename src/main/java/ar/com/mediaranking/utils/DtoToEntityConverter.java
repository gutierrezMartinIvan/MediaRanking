package ar.com.mediaranking.utils;

import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.request.SeriesRequest;
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

    public List<SeriesResponse> convertEntityToDto(List<SeriesEntity> series) {
        return series.stream()
                .map(serie -> convertEntityToDto(serie))
               .collect(Collectors.toList());
    }
}
