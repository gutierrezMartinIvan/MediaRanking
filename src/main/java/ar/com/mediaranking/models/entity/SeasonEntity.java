package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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
    Integer seasonNumber;

    @Column
    @Length(max = 100)
    String title;

    @Column(nullable = false)
    @Size(min = 1, max = 400)
    String Description;


    @Column(nullable = false)
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    List<EpisodeEntity> episodes;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private SeriesEntity series;

}
