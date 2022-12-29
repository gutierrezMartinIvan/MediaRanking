package com.media.ranking.ar.models.entity;

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
