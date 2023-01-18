package ar.com.mediaranking.models.entity.filter;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Builder
@Getter
public class EpisodeFilter {
    Long seriesId;
    Long seasonId;
    Integer seasonNumber;
    Integer episodeNumber;
    Integer year;
    String title;
}
