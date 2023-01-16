package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @NotBlank(message = "Tittle can not be blank")
    @Schema(description = "Series title", example = "Dragon Ball Super")
    String title;

    @NotBlank(message = "Description can not be blank")
    @Schema(description = "Series description", example = "In the year 1992, the two sides of....")
    String description;

    @NotBlank(message = "Author can not be blank")
    @Schema(description = "Series author", example = "Akira Toriyama")
    String author;

    @Min(value=1800)
    @Max(value=2023)
    @NotNull(message = "Year can not be null")
    Integer year;

    @NotNull(message = "Genres can not be null")
    @Schema(description = "Series genres",example = "[\"Action\",\"Adventure\",\"Animation\",\"Comedy\",\"Family\",\"Sci-Fi\"]")
    Set<String> genres = new HashSet<>();

    @Schema(description = "Series seasons, this field is optional")
    List<SeasonSeriesRequest> seasons;
}
