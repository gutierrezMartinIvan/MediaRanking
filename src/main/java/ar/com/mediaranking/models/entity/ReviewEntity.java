package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "reviews")
@Data
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private long id;


    private String userId;

    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String review;

    @Column(nullable = false)
    @Min(value=1)
    @Max(value=10)
    @NotNull(message = "Description can not be null")
    Integer rating;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "movie_entity_id")
    private MovieEntity movieEntity;

    public MovieEntity getMovieEntity() {
        return movieEntity;
    }

    public void setMovieEntity(MovieEntity movieEntity) {
        this.movieEntity = movieEntity;
    }
}
