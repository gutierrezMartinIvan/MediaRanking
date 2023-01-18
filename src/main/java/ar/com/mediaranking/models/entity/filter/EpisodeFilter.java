package ar.com.mediaranking.models.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EpisodeFilter {
    Long seriesId;
    Long seasonId;
    Integer seasonNumber;
    Integer episodeNumber;
    Integer year;
    String title;
}
