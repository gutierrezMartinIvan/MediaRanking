package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.Length;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "seasons")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
    String description;


    @Column(nullable = false)
    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL)
    @OrderBy("episodeNumber ASC")
    @ToString.Exclude
    List<EpisodeEntity> episodes = new ArrayList<>();

    //TODO: Add Year

    @ManyToOne
    @JoinColumn(name = "series_id")
    private SeriesEntity series;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SeasonEntity that = (SeasonEntity) o;
        return id != 0 && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
