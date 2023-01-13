package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import ar.com.mediaranking.models.response.EpisodeResponse;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity,Long> {
    List<EpisodeEntity> findAll(Specification<EpisodeEntity> spec);
}
