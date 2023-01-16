package ar.com.mediaranking.models.request;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieRequest {
    @NotBlank(message = "Description can not be blank")
    @Schema(description = "Movie title", example = "Spider-Man: Into the Spider-Verse")
    String title;

    @NotBlank(message = "Description can not be blank")
    @Schema(description = "Movie description",example = "Teen Miles Morales becomes the Spider-Man of his universe, and must join with five spider-powered individuals from other dimensions to stop a threat for all realities.")
    String description;

    @NotBlank(message = "Description can not be blank")
    @Schema(description = "Movie director",example = "Bob Persichetti")
    String director;

    @Schema(description = "Movie genres",example = "[\"Action\",\"Adventure\",\"Animation\",\"Comedy\",\"Family\",\"Sci-Fi\"]")
    private Set<String> genres = new HashSet<>();

    @Min(value=1)
    @Max(value=9999)
    @Schema(description = "Movie duration in minutes", example = "117")
    Integer  duration;

    @Min(value=1800)
    @Max(value=2300)
    @Schema(example = "2018")
    Integer  year;
}
