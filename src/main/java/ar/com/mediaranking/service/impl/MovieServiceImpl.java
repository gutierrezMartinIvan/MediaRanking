package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.service.MovieService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository repository;

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
        MovieResponse response = mapper.convertEntityToDto(entitySave);
        return response;
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
        return mapper.convertEntityToDto(repository.findById(id).get());
    }

    @Override
    public MovieResponse update(long id, MovieRequest movie){
        MovieEntity entity = mapper.convertDtoToEntity(movie);
        repository.deleteById(id);
        MovieEntity entitySave = repository.save(entity);
        MovieResponse response = mapper.convertEntityToDto(entitySave);
        return response;
    }

    public List<MovieResponse> findByGenre(String genre){
        return mapper.convertMoviesToDto(repository.findAllByGenres(genre));
    }

    public List<MovieResponse> findByFilter(String title, String director, List<String> genres){
        MovieEntity entity = new MovieEntity();
        entity.setTitle(title);
        entity.setDirector(director);
        entity.setGenres(genres);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("title", contains().ignoreCase())
                .withMatcher("director", contains().ignoreCase())
                .withMatcher("genres", contains().ignoreCase())
                .withIgnorePaths("id","description","duration");

        Example x = Example.of(entity);
        return mapper.convertMoviesToDto(repository.findAll(Example.of(entity, matcher)));

    }

}
