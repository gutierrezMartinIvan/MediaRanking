package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class MovieUpdate {
    @Schema(description = "Movie title", example = "Spider-Man: Into the Spider-Verse")
    String title;

    @Schema(description = "Movie description",example = "Teen Miles Morales becomes the Spider-Man of his universe, and must join with five spider-powered individuals from other dimensions to stop a threat for all realities.")
    String description;

    @Schema(description = "Movie director",example = "Bob Persichetti")
    String director;

    @Schema(description = "Movie genres",example = "[\"Action\",\"Adventure\",\"Animation\",\"Comedy\",\"Family\",\"Sci-Fi\"]",
            allowableValues = {
                    "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY", "CRIME",
                    "DOCUMENTARY", "DRAMA", "FAMILY", "FANTASY", "FILM_NOIR", "HISTORY",
                    "HORROR", "MUSIC", "MUSICAL", "MYSTERY", "ROMANCE", "SCI_FI", "SPORT",
                    "THRILLER", "WAR", "WESTERN"}
    )
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
