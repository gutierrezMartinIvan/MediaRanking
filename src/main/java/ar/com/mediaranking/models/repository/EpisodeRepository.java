package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.EpisodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpisodeRepository extends JpaRepository<EpisodeEntity,Long> {

}
