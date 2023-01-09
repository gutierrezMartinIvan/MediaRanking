package ar.com.mediaranking.models.request;

import ar.com.mediaranking.models.entity.GenreEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeriesRequest {
    @NotNull(message = "Tittle can not be null")
    @NotBlank(message = "Tittle can not be blank")
    @NotEmpty(message = "Tittle can not be empty")
    @Schema(description = "Series title", example = "Dragon Ball Super")
    String title;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    @Schema(description = "Series description", example = "In the year 1992, the two sides of....")
    String description;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    @Schema(description = "Series author", example = "Akira Toriyama")
    String author;

    @Min(value=1800)
    @Max(value=2023)
    @NotNull(message = "Description can not be null")
    Integer year;

    @NotNull(message = "Genres can not be null")
    @Schema(description = "Series genres",example = "[\"Action\",\"Adventure\",\"Animation\",\"Comedy\",\"Family\",\"Sci-Fi\"]")
    Set<String> genres = new HashSet<>();
}
