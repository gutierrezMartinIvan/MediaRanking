package ar.com.mediaranking.utils;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.repository.IGenreRepository;
import ar.com.mediaranking.models.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenreSeeder implements CommandLineRunner {

    private final List<String> genres = List.of("Action", "Adventure", "Comedy", "Crime", "Drama", "Fantasy", "Historical",
            "Historical fiction", "Horror", "Magical realism", "Mystery", "Paranoid fiction", "Philosophical", "Political", "Romance",
            "Saga", "Satire", "Science fiction", "Social", "Speculative", "Thriller", "Urban", "Western");

    @Autowired
    private IGenreRepository repository;
    @Autowired
    private IReviewRepository iReviewRepository;

    @Override
    public void run(String... args) throws Exception {
        loadActivity();
    }

    private void loadActivity() {
        for (String genre : genres) {
            if(repository.findByName(genre) == null) {
                repository.save(GenreEntity.builder().name(genre).build());
            }
        }
    }
}
