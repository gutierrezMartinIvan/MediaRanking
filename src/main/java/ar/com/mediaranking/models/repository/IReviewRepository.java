package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReviewRepository extends JpaRepository<ReviewEntity, Long> {

    List<ReviewEntity> findAllByMovies(MovieEntity id, Sort sort);

    List<ReviewEntity> findAllBySeries(SeriesEntity id, Sort rating);

    List<ReviewEntity> findAllByUserId(String id);
}
