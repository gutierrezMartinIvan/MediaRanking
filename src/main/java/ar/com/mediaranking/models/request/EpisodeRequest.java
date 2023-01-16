package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EpisodeRequest {

    @Schema(description = "Season Id", example = "1")
    @NotNull(message = "Season id can not be null")
    @Min(value=1)
    Long seasonId;
    @Schema(description = "Episode number", example = "1")
    @NotNull(message = "Season id can not be null")
    @Min(value=1)
    Integer episodeNumber;

    @NotBlank(message = "Tittle can not be blank")
    @Schema(description = "Episode title", example = "Episode 1")
    String title;

    @NotBlank(message = "Description can not be blank")
    @Schema(description = "Episode description", example = "Episode 1 description")
    String description;

}
