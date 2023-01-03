package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Long> {

    List<MovieEntity> findAllByGenres(String genre);

    List<MovieEntity> findAllByTitleAndDirectorAndGenres(String title, String director, String genre);

}

