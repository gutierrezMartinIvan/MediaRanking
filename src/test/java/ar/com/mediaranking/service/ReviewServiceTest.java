package ar.com.mediaranking.service;


import ar.com.mediaranking.exception.AlreadyExistsException;
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
import ar.com.mediaranking.service.impl.ReviewServiceImpl;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ReviewServiceTest {

    @Mock
    private IReviewRepository repository;

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private ISeriesRepository seriesRepository;

    @Mock
    private DtoToEntityConverter mapper;

    @InjectMocks
    private ReviewServiceImpl service;

    @Test
    public void createReviewForMovieSavesReview() {
        MovieEntity movie = MovieEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        given(movieRepository.findById(1L)).willReturn(Optional.of(movie));

        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewRequest request = ReviewRequest.builder().userId("1L").entityId(1L).rating(5).review("Review").build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.save(review)).willReturn(review);
        given(repository.findByUserIdAndMovies("1L", movie)).willReturn(Optional.empty());
        given(mapper.convertDtoToEntity(request)).willReturn(review);
        given(mapper.convertEntityToDto(review)).willReturn(response);

        ReviewResponse saved = service.createReviewForMovie(request);

        assert(saved.getId() == 1L);
        verify(repository, Mockito.times(1)).save(review);
        verify(movieRepository, Mockito.times(1)).findById(1L);
    }


    @Test
    public void createReviewForMovieOfAReviewThatAlreadyExistThrowsException() {
        MovieEntity movie = MovieEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        given(movieRepository.findById(1L)).willReturn(Optional.of(movie));

        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewRequest request = ReviewRequest.builder().userId("1L").entityId(1L).rating(5).review("Review").build();

        given(repository.save(review)).willReturn(review);
        given(repository.findByUserIdAndMovies("1L", movie)).willReturn(Optional.of(review));

        assertThrows(AlreadyExistsException.class, () -> service.createReviewForMovie(request));
    }

    @Test
    public void createReviewForMovieThatDoesntExistThrowsException() {
        given(movieRepository.findById(1L)).willReturn(Optional.empty());
        ReviewRequest request = ReviewRequest.builder().userId("1L").entityId(1L).rating(5).review("Review").build();

        assertThrows(NotFoundException.class, () -> service.createReviewForMovie(request));
    }

    @Test
    public void createReviewForSeriesSavesReview() {
        SeriesEntity series = SeriesEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));

        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewRequest request = ReviewRequest.builder().userId("1L").entityId(1L).rating(5).review("Review").build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.save(review)).willReturn(review);
        given(repository.findByUserIdAndSeries("1L", series)).willReturn(Optional.empty());
        given(mapper.convertDtoToEntity(request)).willReturn(review);
        given(mapper.convertEntityToDto(review)).willReturn(response);

        ReviewResponse saved = service.createReviewForSeries(request);

        assert(saved.getId() == 1L);
        verify(repository, Mockito.times(1)).save(review);
        verify(seriesRepository, Mockito.times(1)).findById(1L);
    }


    @Test
    public void createReviewForSeriesOfAReviewThatAlreadyExistThrowsException() {
        SeriesEntity series = SeriesEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));

        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewRequest request = ReviewRequest.builder().userId("1L").entityId(1L).rating(5).review("Review").build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.save(review)).willReturn(review);
        given(repository.findByUserIdAndSeries("1L", series)).willReturn(Optional.of(review));

        assertThrows(AlreadyExistsException.class, () -> service.createReviewForSeries(request));
    }

    @Test
    public void createReviewForSeriesThatDoesntExistThrowsException() {
        given(seriesRepository.findById(1L)).willReturn(Optional.empty());
        ReviewRequest request = ReviewRequest.builder().userId("1L").entityId(1L).rating(5).review("Review").build();

        assertThrows(NotFoundException.class, () -> service.createReviewForSeries(request));
    }

    @Test
    void findAllByMovieIdWithNullOrderReturnsAllReviews() {
        MovieEntity movie = MovieEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.findAllByMovies(movie, null)).willReturn(List.of(review));
        given(movieRepository.findById(1L)).willReturn(Optional.of(movie));
        given(mapper.convertReviewsToDto(List.of(review))).willReturn(List.of(response));

        List<ReviewResponse> found = service.findAllByMovieId(1L, null);

        assert(found.get(0).getId() == 1L);
        verify(repository, Mockito.times(1)).findAllByMovies(movie, null);
    }

    @Test
    void findAllByMovieIdWithAscOrderReturnsAllReviewsInAscOrder() {
        MovieEntity movie = MovieEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.findAllByMovies(movie, Sort.by("rating").ascending())).willReturn(List.of(review));
        given(movieRepository.findById(1L)).willReturn(Optional.of(movie));
        given(mapper.convertReviewsToDto(List.of(review))).willReturn(List.of(response));

        List<ReviewResponse> found = service.findAllByMovieId(1L, "asc");

        assert(found.get(0).getId() == 1L);
        verify(repository, Mockito.times(1)).findAllByMovies(movie, Sort.by("rating").ascending());
    }

    @Test
    void findAllByMovieIdWithNotKnownOrderReturnsAllReviewsInDesOrder() {
        MovieEntity movie = MovieEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.findAllByMovies(movie, Sort.by("rating").descending())).willReturn(List.of(review));
        given(movieRepository.findById(1L)).willReturn(Optional.of(movie));
        given(mapper.convertReviewsToDto(List.of(review))).willReturn(List.of(response));

        List<ReviewResponse> found = service.findAllByMovieId(1L, "xd");

        assert(found.get(0).getId() == 1L);
        verify(repository, Mockito.times(1)).findAllByMovies(movie, Sort.by("rating").descending());
    }

    @Test
    void findAllByMovieIdThatDoesntExistThrowsException() {
        given(movieRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findAllByMovieId(1L, null));
    }

    @Test
    void findAllBySeriesIdWithNullOrderReturnsAllReviews() {
        SeriesEntity series = SeriesEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.findAllBySeries(series, null)).willReturn(List.of(review));
        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));
        given(mapper.convertReviewsToDto(List.of(review))).willReturn(List.of(response));

        List<ReviewResponse> found = service.findAllBySeriesId(1L, null);

        assert(found.get(0).getId() == 1L);
        verify(repository, Mockito.times(1)).findAllBySeries(series, null);
    }

    @Test
    void findAllBySeriesIdWithAscOrderReturnsAllReviewsInAscOrder() {
        SeriesEntity series = SeriesEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.findAllBySeries(series, Sort.by("rating").ascending())).willReturn(List.of(review));
        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));
        given(mapper.convertReviewsToDto(List.of(review))).willReturn(List.of(response));

        List<ReviewResponse> found = service.findAllBySeriesId(1L, "asc");

        assert(found.get(0).getId() == 1L);
        verify(repository, Mockito.times(1)).findAllBySeries(series, Sort.by("rating").ascending());
    }

    @Test
    void findAllBySeriesIdWithDesOrderReturnsAllReviewsInDesOrder() {
        SeriesEntity series = SeriesEntity.builder().id(1L).reviews(new ArrayList<>()).build();
        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.findAllBySeries(series, Sort.by("rating").descending())).willReturn(List.of(review));
        given(seriesRepository.findById(1L)).willReturn(Optional.of(series));
        given(mapper.convertReviewsToDto(List.of(review))).willReturn(List.of(response));

        List<ReviewResponse> found = service.findAllBySeriesId(1L, "des");

        assert(found.get(0).getId() == 1L);
        verify(repository, Mockito.times(1)).findAllBySeries(series, Sort.by("rating").descending());
    }

    @Test
    void findAllBySeriesIdThatDoesntExistThrowsException() {
        given(seriesRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.findAllBySeriesId(1L, null));
    }

    @Test
    void findAllByUserIdReturnsAllReviews() {
        ReviewEntity review = ReviewEntity.builder().id(1L).build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(5).review("Review").build();

        given(repository.findAllByUserId("1L")).willReturn(List.of(review));
        given(mapper.convertReviewsToDto(List.of(review))).willReturn(List.of(response));

        List<ReviewResponse> found = service.findAllByUserId("1L");

        assert(found.get(0).getId() == 1L);
        verify(repository, Mockito.times(1)).findAllByUserId("1L");
    }

    @Test
    void updateReviewThatDoesntExistThrowsException() {
        ReviewUpdate update = ReviewUpdate.builder().rating(5).review("Review").build();
        given(repository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.update(1L, update));
    }

    @Test
    void updateReviewUpdatesTheData(){
        ReviewEntity review = ReviewEntity.builder().id(1L).userId("1L").rating(2).review("Update").build();
        ReviewResponse response = ReviewResponse.builder().id(1L).userId("1L").rating(2).review("Update").build();
        ReviewUpdate update = ReviewUpdate.builder().rating(5).review("Review").build();

        given(repository.findById(1L)).willReturn(Optional.of(review));
        review.setRating(update.getRating());
        review.setReview(update.getReview());
        given(repository.save(review)).willReturn(review);
        given(mapper.convertEntityToDto(review)).willReturn(response);

        ReviewResponse found = service.update(1L, update);

        assert(found.getId() == 1L);
        assert (found.getRating() == 2);
        assert (found.getReview().equals("Update"));
        verify(repository, Mockito.times(1)).save(review);
    }
}
