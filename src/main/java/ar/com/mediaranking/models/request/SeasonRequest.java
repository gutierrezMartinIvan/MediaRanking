package ar.com.mediaranking.models.request;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SeasonRequest {
    //Todo add validation
    @Schema(description = "Series id", example = "1")
    Long seriesId;

    @Schema(description = "Season number", example = "1")
    Integer seasonNumber;

    @Schema(description = "Season title", example = "Season 1")
    String title;

    @Schema(description = "Season description", example = "Season 1 description")
    String description;

    List<EpisodeSeasonRequest> episodes;
}
