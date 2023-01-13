package ar.com.mediaranking.models.response;

import ar.com.mediaranking.models.request.EpisodeRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
public class SeasonResponse {
    @Schema(example = "1")
    Long id;

    @Schema(description = "Season number", example = "1")
    Integer seasonNumber;
    @Schema(description = "Season title", example = "Season 1")
    String title;
    @Schema(description = "Season description", example = "Season 1 description")
    String Description;
    @Schema(description = "Season episodes")
    List<EpisodeResponse> episodes;
}
