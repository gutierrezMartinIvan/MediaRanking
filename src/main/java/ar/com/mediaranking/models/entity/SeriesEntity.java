package ar.com.mediaranking.models.entity;

import java.util.List;

public class SeriesEntity {
    long id;
    String title;
    String description;
    String author;
    List<String> genres;
    String dateRelease;
    List<SeasonEntity> seasons;
    ReviewEntity review;
}

