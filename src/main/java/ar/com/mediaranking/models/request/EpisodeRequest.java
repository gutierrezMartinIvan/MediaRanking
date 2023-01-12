package ar.com.mediaranking.models.request;

import ar.com.mediaranking.models.entity.SeasonEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.bind.annotation.Mapping;

@Data
public class EpisodeRequest {
    //Todo add validation


    @Schema(description = "Season Id", example = "1")
    Long seasonId;
    @Schema(description = "Episode number", example = "1")
    Integer episodeNumber;
    @Schema(description = "Episode title", example = "Episode 1")
    String title;
    @Schema(description = "Episode description", example = "Episode 1 description")
    String description;

}
