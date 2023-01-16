package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.util.List;

@Data
public class SeasonSeriesRequest {
    @Schema(description = "Season number", example = "1")
    @Min(value=1)
    @Max(value=30)
    @NotNull(message = "Season number can not be null")
    Integer seasonNumber;

    @NotBlank(message = "Tittle can not be blank")
    @Schema(description = "Season title", example = "Season 1")
    String title;

    @NotBlank(message = "Description can not be blank")
    @Schema(description = "Season description", example = "Season 1 description")
    String description;

    @NotNull(message = "Episodes can not be null")
    @Schema(description = "Season episodes, this field is optional")
    List<EpisodeSeasonRequest> episodes;
}
