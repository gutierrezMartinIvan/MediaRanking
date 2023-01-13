package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class SeriesUpdateRequest {
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
