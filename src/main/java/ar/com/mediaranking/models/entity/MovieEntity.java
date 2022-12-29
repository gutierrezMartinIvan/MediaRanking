package ar.com.mediaranking.models.entity;

import java.util.List;

public class MovieEntity {
    long id;
    String title;
    String description;
    String director;
    List<String> genres;
    int  duration;
    ReviewEntity review;
}
