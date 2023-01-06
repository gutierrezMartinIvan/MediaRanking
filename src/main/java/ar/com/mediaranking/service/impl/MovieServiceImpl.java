package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.service.MovieService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;

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
        MovieEntity entity = mapper.convertDtoToEntity(request);
        MovieEntity entitySave = repository.save(entity);
        return mapper.convertEntityToDto(entitySave);
    }

    /*
    public List<MovieResponse> findAll(String genre) {
        List<MovieEntity> entities = repository.findAllByGenres(genre);
        List<MovieResponse> responses = mapper.convertEntityToDto(entities);
        return responses;
    }*/

    @Override
    public List<MovieResponse> findAll() {
        return mapper.convertMoviesToDto(repository.findAll());
    }

    @Override
    public void deleteById(long id){
        repository.deleteById(id);
    }

    @Override
    public MovieResponse findById(Long id){
        return mapper.convertEntityToDto(repository.findById(id).orElse(null));
    }

    @Override
    public MovieResponse update(long id, MovieRequest movie){
        MovieEntity entity = mapper.convertDtoToEntity(movie);
        repository.deleteById(id);
        MovieEntity entitySave = repository.save(entity);
        return mapper.convertEntityToDto(entitySave);
    }

    public List<MovieResponse> findByGenre(String genre){
        return mapper.convertMoviesToDto(repository.findAllByGenres(genre));
    }
    

    public List<MovieResponse> findByFilter(String title, String director, Set<String> genres){
        MovieEntity entity = new MovieEntity();
        entity.setTitle(title);
        entity.setDirector(director);

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withMatcher("title", contains().ignoreCase())
                .withMatcher("director", contains().ignoreCase())
                .withIgnorePaths("id","description","duration");

        List<MovieEntity> entities = repository.findAll(Example.of(entity, matcher));

        if(genres != null && !genres.isEmpty()){
            entities.removeIf(movie -> !movie.getGenres().containsAll(genres));
        }

        return mapper.convertMoviesToDto(entities);
    }

    public MovieResponse addReview(long id, ReviewRequest review){
        MovieEntity entity = repository.findById(id).orElse(null);
        ReviewEntity reviewEntity = reviewService.save(review);
        entity.getReviews().add(reviewEntity);
        MovieEntity entitySave = repository.save(entity);
        return mapper.convertEntityToDto(entitySave);
    }

}
