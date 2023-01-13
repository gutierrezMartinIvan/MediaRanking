package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.MovieAlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.service.MovieService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import java.util.Set;


import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository repository;

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private DtoToEntityConverter mapper;

    @Override
    public boolean isNull(MovieRequest request) {
        return false;
    }


    @Override
    public MovieResponse save(MovieRequest request) /*throws NameOrContentAreNull*/ {
        repository.findByTitleAndYear(request.getTitle(), request.getYear()).ifPresent(movieEntity -> {
            throw new MovieAlreadyExistsException(
                    "There is already a movie with the name: " + request.getTitle() +
                    " and year: " + request.getYear() +
                    " with id :" + movieEntity.getId()
            );
        });

        MovieEntity entity = mapper.convertDtoToEntity(request);
        MovieEntity entitySave = repository.save(entity);
        return mapper.convertEntityToDto(entitySave);
    }

    public List<MovieResponse> saveList(List<MovieRequest> movieList){
        List<MovieResponse> movieEntityList = new ArrayList<>();
        for (MovieRequest movieRequest : movieList) {
            movieEntityList.add(save(movieRequest));
        }
        return movieEntityList;
    }

    @Override
    public List<MovieResponse> findAll() {
        return mapper.convertMoviesToDto(repository.findAll());
    }

    @Override
    public void deleteById(long id){
        repository.deleteById(id);
    }

    @Override
    public MovieResponse findById(Long id) {
        MovieEntity movie = repository.findById(id).orElseThrow(() -> new NotFoundException("Movie with id: " + id + " not found"));
        return mapper.convertEntityToDto(movie);
    }

    @Override
    public MovieResponse update(long id, MovieRequest movie){
        // TODO: change exception name
        MovieEntity entity = repository.findById(id).orElseThrow(() -> new NotFoundException("There is not a movie with the id: " + id));

        if(movie.getTitle() != null && !movie.getTitle().isBlank()){
            entity.setTitle(movie.getTitle());
        }
        if(movie.getDirector() != null && !movie.getDirector().isBlank()){
            entity.setDirector(movie.getDirector());
        }
        if(movie.getGenres() != null && !movie.getGenres().isEmpty()){
            entity.setGenres(mapper.convertSetStringToGenre(movie.getGenres()));
        }
        if(movie.getDuration() != null && movie.getDuration() > 0){
            entity.setDuration(movie.getDuration());
        }
        if(movie.getYear() != null && movie.getYear() > 0){
            entity.setYear(movie.getYear());
        }

        MovieEntity updatedEntity = repository.save(entity);
        return mapper.convertEntityToDto(updatedEntity);
    }


    public List<MovieResponse> findByFilter(String title, String director,Integer year, Integer minDuration, Integer maxDuration, Set<String> genres){
        Specification<MovieEntity> spec = where(null);


        if(title != null){
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%"));
        }
        if(director != null){
            spec = spec.and((root, query, cb) -> cb.like(cb.lower(root.get("director")), "%" + director.toLowerCase() + "%"));
        }
        if(year != null){
            spec = spec.and((root, query, cb) -> cb.equal(root.get("year"), year));
        }
        if(minDuration != null){
            spec = spec.and((root, query, cb) -> cb.greaterThanOrEqualTo(root.get("duration"), minDuration));
        }
        if(maxDuration != null){
            spec = spec.and((root, query, cb) -> cb.lessThanOrEqualTo(root.get("duration"), maxDuration));
        }
        if(genres != null){
            spec = spec.and((root, query, cb) -> {
                Join<MovieEntity, GenreEntity> join = root.join("genres");
                CriteriaBuilder.In<String> in = cb.in(join.get("name"));
                mapper.convertSetStringToGenre(genres).forEach(genre -> in.value(genre.getName()));
                return in;
            });
        }

        return mapper.convertMoviesToDto(repository.findAll(spec));

    }


}
