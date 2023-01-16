package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.repository.IReviewRepository;
import ar.com.mediaranking.models.repository.ISeriesRepository;
import ar.com.mediaranking.models.repository.MovieRepository;
import ar.com.mediaranking.models.request.ReviewRequest;
import ar.com.mediaranking.models.request.ReviewUpdate;
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
    private DtoToEntityConverter mapper;
    @Autowired
    private ISeriesRepository seriesRepository;

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
        MovieEntity movie = movieRepository.findById(id).orElseThrow(() -> new NotFoundException("Movie with id: " + id + " not found"));


        return mapper.convertReviewsToDto(repository.findAllByMovies(movie, sort));
    }

    @Override
    public List<ReviewResponse>  findAllBySeriesId(Long id, String order){
        Sort sort = getSort(order, "rating");
        SeriesEntity series = seriesRepository.findById(id).orElseThrow(() -> new NotFoundException("Series with id: " + id + " not found"));
        return mapper.convertReviewsToDto(repository.findAllBySeries(series, sort));
    }

    @Override
    public List<ReviewResponse>  findAllByUserId(String id){
        return mapper.convertReviewsToDto(repository.findAllByUserId(id));
    }

    @Override
    public ReviewResponse update(Long id,ReviewUpdate reviewRequest){
        ReviewEntity review = repository.findById(id).orElseThrow(() -> new NotFoundException("Review with id: " + id + " not found"));

        if(reviewRequest.getReview() != null) {
            review.setReview(reviewRequest.getReview());
        }
        if(reviewRequest.getRating() != null) {
            review.setRating(reviewRequest.getRating());
        }

        return mapper.convertEntityToDto(repository.save(review));
    }

    @Override
    public ReviewResponse createReviewForMovie(ReviewRequest review) {
        MovieEntity movie = movieRepository.findById(review.getEntityId()).orElseThrow(() -> new NotFoundException("Movie with id: " + review.getEntityId() + " not found"));
        ReviewEntity reviewEntity = mapper.convertDtoToEntity(review);

        reviewEntity.setMovies(movie);
        movie.getReviews().add(reviewEntity);

        movieRepository.save(movie);
        return mapper.convertEntityToDto(repository.save(reviewEntity));
    }

    @Override
    public ReviewResponse createReviewForSeries(ReviewRequest review) {
        SeriesEntity series = seriesRepository.findById(review.getEntityId()).orElseThrow(() -> new NotFoundException("Series with id: " + review.getEntityId() + " not found"));
        ReviewEntity reviewEntity = mapper.convertDtoToEntity(review);

        reviewEntity.setSeries(series);
        series.getReviews().add(reviewEntity);

        seriesRepository.save(series);
        return mapper.convertEntityToDto(repository.save(reviewEntity));
    }

}

