package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.SeriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISeriesRepository extends JpaRepository<SeriesEntity,Long> {
}
