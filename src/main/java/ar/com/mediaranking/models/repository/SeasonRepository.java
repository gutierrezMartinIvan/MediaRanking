package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.SeasonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonRepository extends JpaRepository<SeasonEntity,Long> {

}
