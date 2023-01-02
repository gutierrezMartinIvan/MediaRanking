package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "series")
public class SeriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "series_id")
    Long id;

    @Column(nullable = false)
    @NotNull(message = "Tittle can not be null")
    @NotBlank(message = "Tittle can not be blank")
    @NotEmpty(message = "Tittle can not be empty")
    @Pattern(regexp =  "^[A-Za-z0-9!@#$%^&*()_+=-]*$", message = "Tittle only can contain letters, numbers and special characters")
    String title;
    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String description;
    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String author;

    @Column(nullable = false)
    @Min(1800)
    @Max(2023)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    Short year;

    /*
    List<String> genres;
    String dateRelease;
    List<SeasonEntity> seasons;
    ReviewEntity review;*/
}

