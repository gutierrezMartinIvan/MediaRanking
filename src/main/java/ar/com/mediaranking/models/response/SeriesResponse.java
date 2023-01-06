package ar.com.mediaranking.models.response;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class SeriesResponse {

    Long id;
    @NotNull(message = "Tittle can not be null")
    @NotBlank(message = "Tittle can not be blank")
    @NotEmpty(message = "Tittle can not be empty")
    @Pattern(regexp =  "^[A-Za-z0-9!@#$%^&*()_+=-]*$", message = "Tittle only can contain letters, numbers and special characters")
    String title;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String description;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String author;

    @Column(nullable = false)
    @Min(value=1800)
    @Max(value=2023)
    @NotNull(message = "Description can not be null")
    Integer year;

    @NotNull(message = "Genres can not be null")
    List<GenreEntity> genres;

    private List<ReviewResponse> reviews;
}