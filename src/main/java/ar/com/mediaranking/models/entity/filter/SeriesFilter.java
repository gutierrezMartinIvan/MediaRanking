package ar.com.mediaranking.models.entity.filter;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SeriesFilter {
    private String tittle;
    private String author;
    private Integer year;
    private List<String> genres;
}
