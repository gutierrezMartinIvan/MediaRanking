package ar.com.mediaranking.models.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.Set;


@AllArgsConstructor
@Getter
public class MovieFilter {
    private String title;
    private String director;
    private Integer year;
    private Integer minDuration;
    private Integer maxDuration;
    private Set<String> genres;
}
