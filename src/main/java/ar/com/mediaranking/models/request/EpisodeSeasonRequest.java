package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class EpisodeSeasonRequest {
    @Schema(description = "Episode number", example = "1")
    Integer episodeNumber;
    @Schema(description = "Episode title", example = "Episode 1")
    @NotNull(message = "The name can't be null")
    @NotEmpty(message = "the name can't be empty")
    @NotBlank(message = "the name can't be blank")
    String title;
    @Schema(description = "Episode description", example = "Episode 1 description")
    String description;
}
