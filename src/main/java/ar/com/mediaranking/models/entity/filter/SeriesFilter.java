package ar.com.mediaranking.models.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SeriesFilter {
    private String title;
    private String author;
    private Integer year;
    private List<String> genres;
}
