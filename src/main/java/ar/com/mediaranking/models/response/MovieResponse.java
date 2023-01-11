package ar.com.mediaranking.models.response;

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
public class MovieResponse {
    Long id;
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    @Schema(description = "Movie title", example = "Shutter Island")
    String title;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    @Schema(example = "In 1954, a U.S. Marshal investigates the disappearance of a murderess who escaped from a hospital for the criminally insane.")
    String description;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    @Schema(example = "Martin Scorsese")
    String director;

    @Schema(example = "[\"Drama\",\"Mystery\",\"Thriller\"]")
    private Set<String> genres = new HashSet<>();;

    @Min(value=1)
    @Max(value=9999)
    @Schema(example = "138")
    Integer duration;

    @Min(value=1800)
    @Max(value=2300)
    @Schema(example = "2010")
    Integer year;

}
