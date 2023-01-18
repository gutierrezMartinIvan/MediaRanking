package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.*;

@Entity
@Table(name = "movies")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String description;

    //TODO list of directors
    @Column(nullable = false)
    String director;

    @Column(nullable = false)
    @ManyToMany
    @JoinTable(name = "movies_genres",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @ToString.Exclude
    private Set<GenreEntity> genres = new HashSet<>();

    @Column(nullable = false)
    @Min(value=1)
    @Max(value=9999)
    Integer  duration;

    @Column(nullable = false)
    @Min(value=1800)
    @Max(value=2300)
    Integer  year;


    @OneToMany(mappedBy = "movies", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    List<ReviewEntity> reviews = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MovieEntity movie = (MovieEntity) o;
        return Objects.equals(id, movie.id) && Objects.equals(title, movie.title) && Objects.equals(description, movie.description) && Objects.equals(director, movie.director) && Objects.equals(genres, movie.genres) && Objects.equals(duration, movie.duration) && Objects.equals(year, movie.year) && Objects.equals(reviews, movie.reviews);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
