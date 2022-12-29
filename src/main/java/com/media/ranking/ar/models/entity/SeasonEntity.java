package com.media.ranking.ar.models.entity;

import java.util.List;

public class SeasonEntity {
    long id;
    int number;
    String Description;
    List<EpisodeEntity> episodes;
    ReviewEntity review;
}
