package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<ReviewEntity, Long> {
}
