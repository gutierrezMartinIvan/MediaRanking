package ar.com.mediaranking.models.response;

import ar.com.mediaranking.models.entity.GenreEntity;
import ar.com.mediaranking.models.entity.ReviewEntity;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "Series title", example = "Dragon Ball")
    String title;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    @Schema(example = "Goku saves the earth after...")
    String description;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    @Schema(example = "Akira")
    String author;

    @Column(nullable = false)
    @Min(value=1800)
    @Max(value=2023)
    @NotNull(message = "Description can not be null")
    @Schema(example = "2010")
    Integer year;

    @NotNull(message = "Genres can not be null")
    @Schema(example = "[\"Drama\",\"Mystery\",\"Thriller\"]")
    List<String> genres;
    @Schema(description = "Reviews of the series")
    private List<ReviewResponse> reviews;
}
