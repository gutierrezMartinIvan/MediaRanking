package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.IReviewRepository;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.response.ReviewResponse;
import ar.com.mediaranking.service.IReviewService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements IReviewService {

    @Autowired
    private IReviewRepository repository;
    @Autowired
    private DtoToEntityConverter mapper;

    @Override
    public ReviewEntity save(ReviewRequest reviewRequest) {
        ReviewEntity entity = mapper.convertDtoToEntity(reviewRequest);
        return repository.save(entity);
    }

    @Override
    public ReviewEntity saveSeries(ReviewRequest reviewRequest, SeriesEntity series) {
        ReviewEntity entitySaved = mapper.convertDtoToEntity(reviewRequest);
        entitySaved.setSeries(series);
        repository.save(entitySaved);
        return entitySaved;
    }

    @Override
    public ReviewEntity saveMovie(ReviewRequest reviewRequest, MovieEntity movie) {
        ReviewEntity entitySaved = mapper.convertDtoToEntity(reviewRequest);
        entitySaved.setMovies(movie);
        repository.save(entitySaved);
        return entitySaved;
    }

    public List<ReviewEntity> findAllByMovieId(MovieEntity id, String order){
        if(order == null){
            return repository.findAllByMovies(id);
        }
        if(order.equals("ASC")){
            return repository.findAllByMovies(id, Sort.by(Sort.Direction.ASC, "rating"));
        }
        return repository.findAllByMovies(id, Sort.by(Sort.Direction.DESC, "rating"));

    }
}

