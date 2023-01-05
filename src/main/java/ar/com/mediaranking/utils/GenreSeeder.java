package ar.com.mediaranking.utils;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.repository.IGenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GenreSeeder implements CommandLineRunner {

    @Autowired
    private IGenreRepository repository;
    @Override
    public void run(String... args) throws Exception {
        loadActivity();
    }

    private void loadActivity() {
        if (repository.findAll().isEmpty()){
            repository.save(GenreEntity.builder().name("Action").build());
            repository.save(GenreEntity.builder().name("Comedy").build());
            repository.save(GenreEntity.builder().name("Fantasy").build());
            repository.save(GenreEntity.builder().name("Terror").build());
            repository.save(GenreEntity.builder().name("Horror").build());
        }
    }
}
