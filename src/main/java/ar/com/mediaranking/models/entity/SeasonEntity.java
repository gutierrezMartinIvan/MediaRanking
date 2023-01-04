package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
@Entity
@Table(name = "seasons")
@Data
public class SeasonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id")
    long id;

    @Column(nullable = false)
    @Min(value=1)
    @Max(value=99)
    int number;

    @Column(nullable = false)
    @Size(min = 1, max = 400)
    String Description;
    /*
    @Column(nullable = false)
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    List<EpisodeEntity> episodes;


    @Column(nullable = false)
    List<ReviewEntity> review;*/
}
