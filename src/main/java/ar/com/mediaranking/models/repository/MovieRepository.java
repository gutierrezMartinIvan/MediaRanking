package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository extends JpaRepository<MovieEntity,Long> {

    Optional<MovieEntity> findByTitleAndYear(String title, Integer year);

    List<MovieEntity> findAll(Specification<MovieEntity> spec);
}

