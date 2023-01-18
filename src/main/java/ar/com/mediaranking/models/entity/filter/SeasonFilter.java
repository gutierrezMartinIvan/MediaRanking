package ar.com.mediaranking.models.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SeasonFilter {
    String title;
    Integer seasonNumber;
    Integer year;
    Long seriesId;
}
