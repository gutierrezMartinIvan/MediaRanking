package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.SeriesNotFoundException;
import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.models.response.SeriesResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.service.MovieService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.*;
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

    /*
    public static Specification<MovieEntity> getMoviesByTasteIn(Set<String> taste) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.in(root.get(TASTE)).value(taste);
    }

    public static Specification<MovieEntity> getDurationInBetween(float minPrice, float maxPrice) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.between(root.get(DURATION), minPrice, maxPrice);
    }

    public static Specification<MovieEntity> getMoviesTitle(String title_name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get(title), "%" + title_name + "%");
    }*/

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
        // TODO: change exception name
        MovieEntity entity = repository.findById(id).orElseThrow(() -> new SeriesNotFoundException("There is not a movie with the id: " + id));

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

    public List<MovieResponse> findByGenre(String genre){
        return mapper.convertMoviesToDto(repository.findAllByGenres(genre));
    }
    

    public List<MovieResponse> findByFilter(String title, String director,Integer year, Integer minDuration, Integer maxDuration, Set<String> genres){
        Specification<List<MovieEntity>> spec = where(null);

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

    @Override
    public MovieResponse insertReview2Movie(Long id, ReviewRequest review) {
        MovieEntity entityUpdated = null;
        Optional<MovieEntity> movieOptional = repository.findById(id);

        if (movieOptional.isPresent())
            entityUpdated = movieOptional.get();

        ReviewEntity reviewSaved = reviewService.saveMovie(review, movieOptional.get());
        entityUpdated.getReviews().add(reviewSaved);
        repository.save(entityUpdated);
        return mapper.convertEntityToDto(entityUpdated);
    }

}
