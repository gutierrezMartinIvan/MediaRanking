package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.request.MovieRequest;
import ar.com.mediaranking.models.response.MovieResponse;
import ar.com.mediaranking.service.MovieService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<MovieResponse> findAll( String title, String director, String genre) {
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
}
