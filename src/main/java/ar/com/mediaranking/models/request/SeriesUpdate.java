package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class SeriesUpdate {

    @Schema(description = "Series title, this field is optional and if it is blank it will be ignore", example = "Dragon Ball Super")
    String title;

    @Schema(description = "Series description, this field is optional and if it is blank it will be ignore", example = "In the year 1992, the two sides of....")
    String description;

    @Schema(description = "Series author, this field is optional and if it is blank it will be ignore", example = "Akira Toriyama")
    String author;

    @Min(value = 1800)
    @Max(value = 2023)
    Integer year;

    @Schema(description = "Series genres",example = "[\"Action\",\"Adventure\",\"Animation\",\"Comedy\",\"Family\",\"Sci-Fi\"]",
            allowableValues = {
                    "ACTION", "ADVENTURE", "ANIMATION", "BIOGRAPHY", "COMEDY", "CRIME",
                    "DOCUMENTARY", "DRAMA", "FAMILY", "FANTASY", "FILM_NOIR", "HISTORY",
                    "HORROR", "MUSIC", "MUSICAL", "MYSTERY", "ROMANCE", "SCI_FI", "SPORT",
                    "THRILLER", "WAR", "WESTERN"}
    )
    Set<String> genres = new HashSet<>();
}
