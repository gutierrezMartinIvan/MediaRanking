package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "series")
@Data
public class SeriesEntity {
    @Id
    @GeneratedValue
    @Column(name = "series_id")
    private Long id;

    @Column(nullable = false)
    @NotNull(message = "Tittle can not be null")
    @NotBlank(message = "Tittle can not be blank")
    @NotEmpty(message = "Tittle can not be empty")
    @Pattern(regexp =  "^[A-Za-z0-9!@#$%^&*()_+=-]*$", message = "Tittle only can contain letters, numbers and special characters")
    private String title;
    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    private String description;
    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    private String author;

    @Column(nullable = false)
    @Min(value=1800)
    @Max(value=2023)
    @NotNull(message = "Description can not be null")
    private Integer year;

    @ElementCollection
    private List<String> genres;


    /*
    List<String> genres;
    String dateRelease;
    List<SeasonEntity> seasons;
    ReviewEntity review;*/
}

