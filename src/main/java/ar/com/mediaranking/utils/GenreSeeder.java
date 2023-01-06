package ar.com.mediaranking.utils;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.repository.IGenreRepository;
import ar.com.mediaranking.models.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class GenreSeeder implements CommandLineRunner {

    @Autowired
    private IGenreRepository repository;
    @Autowired
    private IReviewRepository iReviewRepository;

    @Override
    public void run(String... args) throws Exception {
        loadActivity();
    }

    private void loadActivity() {
        for (Genres genre : Genres.values()) {
            if(repository.findByName(genre.getDisplayName()) == null) {
                repository.save(GenreEntity.builder().name(genre.getDisplayName()).build());
            }
        }
    }
}
