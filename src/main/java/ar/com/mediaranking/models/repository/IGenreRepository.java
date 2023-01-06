package ar.com.mediaranking.models.repository;

import ar.com.mediaranking.models.entity.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGenreRepository extends JpaRepository<GenreEntity, Long> {

    GenreEntity findByName(String name);
}
