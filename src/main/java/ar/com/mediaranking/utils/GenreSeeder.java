package ar.com.mediaranking.utils;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.Role;
import ar.com.mediaranking.models.entity.UserEntity;
import ar.com.mediaranking.models.repository.IGenreRepository;
import ar.com.mediaranking.models.repository.UserRepository;
import ar.com.mediaranking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class GenreSeeder implements CommandLineRunner {

    @Autowired
    private IGenreRepository genreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        loadActivity();
        loadAdmin();
    }

    private void loadActivity() {
        for (Genres genre : Genres.values()) {
            if(genreRepository.findByName(genre.getDisplayName()).isEmpty()) {
                genreRepository.save(GenreEntity.builder().name(genre.getDisplayName()).build());
            }
        }
    }

    private void loadAdmin() {
        UserEntity admin = UserEntity.builder()
                .email("admin")
                .password(passwordEncoder.encode("admin"))
                .firstName("admin")
                .lastName("admin")
                .role(Role.ADMIN).build();
        userRepository.save(admin);
    }
}
