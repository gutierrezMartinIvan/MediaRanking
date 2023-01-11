package ar.com.mediaranking.models.response;

import lombok.Data;

@Data
public class EpisodeResponse {
    Long id;
    Integer episodeNumber;
    String title;
    String description;
}
