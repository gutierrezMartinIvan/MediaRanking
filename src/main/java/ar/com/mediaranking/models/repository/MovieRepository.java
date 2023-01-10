package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.MovieEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Long>, JpaSpecificationExecutor {

    List<MovieEntity> findAllByGenres(String genre);

    List<MovieEntity> findAllByTitleAndDirectorAndGenres(String title, String director, String genre);

    @Query("SELECT m FROM MovieEntity m LEFT JOIN FETCH m.reviews r WHERE m.id = :id" )
    MovieEntity findByIdWithReviewsSorted(@Param("id") Long id, Sort sortOrder);

    Optional<MovieEntity> findByTitleAndYear(String title, Integer year);
}

