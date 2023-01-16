package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SeasonUpdateRequest {
    @Schema(description = "Series id, this field is optional", example = "1")
    Long seriesId;

    @Schema(description = "Season number, this field is optional", example = "1")
    Integer seasonNumber;

    @Schema(description = "Season title, this field is optional and if it is blank it will be ignore", example = "Season 1")
    String title;

    @Schema(description = "Season description, this field is optional and if it is blank it will be ignore", example = "Season 1 description")
    String description;

}
