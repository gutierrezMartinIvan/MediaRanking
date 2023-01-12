package ar.com.mediaranking.utils;

import ar.com.mediaranking.models.entity.*;
import ar.com.mediaranking.models.request.*;
import ar.com.mediaranking.models.response.*;
import org.modelmapper.Condition;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.Mapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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

    public ReviewEntity convertDtoToEntity(ReviewRequest reviewRequest) {
        return ReviewEntity.builder()
                .userId(reviewRequest.getUserId())
                .rating(reviewRequest.getRating())
                .review(reviewRequest.getReview())
                .build();
    }

    public ReviewResponse convertEntityToDto(ReviewEntity entity) {
            return modelMapper.map(entity, ReviewResponse.class);
    }

    public List<ReviewResponse> convertReviewsToDto(List<ReviewEntity> entities) {
        return entities.stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }


    public Set<GenreEntity> convertSetStringToGenre(Set<String> genres) {
        if (genres == null) {
            return null;
        }
        Set<GenreEntity> set = new HashSet<>();
        for (String genre : genres) {
            set.add( modelMapper.map(genre, GenreEntity.class));
        }
        return set;
    }

    public void updateEntity(SeriesEntity savedSeries, SeriesEntity updatedSeries) {
        if (updatedSeries.getAuthor() != null)
            savedSeries.setAuthor(updatedSeries.getAuthor());

        if (updatedSeries.getTitle() != null)
            savedSeries.setTitle(updatedSeries.getTitle());

        if (updatedSeries.getDescription() != null)
            savedSeries.setDescription(updatedSeries.getDescription());

        if (updatedSeries.getYear() != null)
            savedSeries.setYear(updatedSeries.getYear());

        if (updatedSeries.getGenres() != null)
            savedSeries.setGenres(updatedSeries.getGenres());
    }

    public SeasonEntity convertDtoToEntity(SeasonRequest season) {
        return modelMapper.map(season, SeasonEntity.class);
    }

    public EpisodeEntity convertDtoToEntity(EpisodeRequest episode) {
        return modelMapper.map(episode, EpisodeEntity.class);
    }

    public SeasonResponse convertEntityToDto(SeasonEntity season) {
        return modelMapper.map(season, SeasonResponse.class);
    }

    public EpisodeResponse convertEntityToDto(EpisodeEntity episodeEntity) {
        return modelMapper.map(episodeEntity, EpisodeResponse.class);
    }

    public EpisodeEntity convertDtoToEntity(EpisodeSeasonRequest episodeRequest) {
        return modelMapper.map(episodeRequest, EpisodeEntity.class);
    }

    public SeasonEntity convertDtoToEntity(SeasonSeriesRequest episodeRequest) {
        return modelMapper.map(episodeRequest, SeasonEntity.class);
    }
}
