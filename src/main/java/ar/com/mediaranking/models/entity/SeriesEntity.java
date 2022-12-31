package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "series")
public class SeriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    /*
    String title;
    String description;
    String author;
    List<String> genres;
    String dateRelease;
    List<SeasonEntity> seasons;
    ReviewEntity review;*/
}

