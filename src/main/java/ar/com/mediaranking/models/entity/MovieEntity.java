package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "series")
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    /*String title;
    String description;
    String director;
    List<String> genres;
    int  duration;
    ReviewEntity review;*/
}
