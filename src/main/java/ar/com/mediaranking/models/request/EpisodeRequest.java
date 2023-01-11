package ar.com.mediaranking.models.request;

import ar.com.mediaranking.models.entity.SeasonEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class EpisodeRequest {
    Integer episodeNumber;
    String title;
    String description;

}
