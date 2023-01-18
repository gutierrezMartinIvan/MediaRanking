package ar.com.mediaranking.models.entity.filter;

import lombok.Builder;
import lombok.Data;

import java.util.Set;


@Data
@Builder
public class MovieFilter {
    private String title;
    private String director;
    private Integer year;
    private Integer minDuration;
    private Integer maxDuration;
    private Set<String> genres;
}
