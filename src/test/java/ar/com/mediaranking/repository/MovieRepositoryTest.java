package ar.com.mediaranking.repository;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.MovieEntity;
import ar.com.mediaranking.models.repository.MovieRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

@DataJpaTest
public class MovieRepositoryTest {

    @Autowired
    private MovieRepository repository;

    /*@Test
    void  saveMovieSavesData() {
        MovieEntity movieEntity = MovieEntity.builder()
                .title("Movie 1")
                .description("Movie 1 description")
                .director("Director 1")
                .year(2021)
                .duration(120)
                .genres(Set.of(GenreEntity.builder().name("Action").build()))
                .build();

        MovieEntity savedMovie = repository.save(movieEntity);

        assert savedMovie.getId() > 0;
        assert savedMovie.getTitle().equals("Movie 1");
    }*/
}
