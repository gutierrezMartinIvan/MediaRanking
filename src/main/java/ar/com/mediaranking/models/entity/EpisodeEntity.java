package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Table(name = "episodes")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episodes_id")
    Long id;

    @Column(nullable = false)
    @NotNull(message = "Tittle can not be null")
    @NotBlank(message = "Tittle can not be blank")
    @NotEmpty(message = "Tittle can not be empty")
    String title;

    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String description;

    @Column(nullable = false)
    @Min(value=1)
    @Max(value=99)
    @NotNull(message = "Description can not be null")
    Integer episodeNumber;


    @ManyToOne
    @JoinColumn(name = "season_id")
    private SeasonEntity season;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EpisodeEntity episode = (EpisodeEntity) o;
        return id != null && Objects.equals(id, episode.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
