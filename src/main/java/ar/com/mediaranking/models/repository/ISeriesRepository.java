package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.SeasonEntity;
import ar.com.mediaranking.models.entity.SeriesEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISeriesRepository extends JpaRepository<SeriesEntity,Long> {
    List<SeriesEntity> findAll(Specification<SeriesEntity> spec);

    SeriesEntity findBySeasons(SeasonEntity season);
}
