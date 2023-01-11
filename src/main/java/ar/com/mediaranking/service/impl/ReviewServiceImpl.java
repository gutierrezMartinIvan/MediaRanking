package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.SeriesNotFoundException;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.IReviewRepository;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.MovieRepository;
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
    private MovieRepository movieRepository;

    @Autowired
    private IReviewRepository reviewRepository;

    @Autowired
    private DtoToEntityConverter mapper;
    @Autowired
    private ISeriesRepository seriesRepository;

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

    private Sort getSort(String order, String field) {
        if(order == null || order.isEmpty()) {
            return null;
        }else if (order.equalsIgnoreCase("asc")) {
            return Sort.by(Sort.Direction.ASC, field);
        }

        return Sort.by(Sort.Direction.DESC, field);

    }

    @Override
    public List<ReviewResponse> findAllByMovieId(Long id, String order) {
        Sort sort = getSort(order, "rating");
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new SeriesNotFoundException("Movie with id: " + id + " not found"));


        return mapper.convertReviewsToDto(repository.findAllByMovies(movie, sort));
    }

    @Override
    public List<ReviewResponse>  findAllBySeriesId(Long id, String order){
        Sort sort = getSort(order, "rating");
        SeriesEntity series = seriesRepository.findById(id).orElseThrow(() -> new SeriesNotFoundException("Series with id: " + id + " not found"));
        return mapper.convertReviewsToDto(repository.findAllBySeries(series, sort));
    }

    @Override
    public List<ReviewResponse>  findAllByUserId(String id){
        return mapper.convertReviewsToDto(repository.findAllByUserId(id));
    }

    @Override
    public ReviewResponse update(Long id, ReviewRequest reviewRequest){
        return null;
    }

}

