package ar.com.mediaranking.models.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Set;

@AllArgsConstructor
@Getter
public class SeriesFilter {
    private String title;
    private String author;
    private Integer year;
    private Set<String> genres;
}
