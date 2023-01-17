package ar.com.mediaranking.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    List<EpisodeResponse> episodes = new ArrayList<>();
}
