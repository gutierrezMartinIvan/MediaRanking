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
    private Long id;
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String review;

    @Column(nullable = false)
    @Min(value=1)
    @Max(value=10)
    @NotNull(message = "raiting can not be null")
    Integer rating;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movies;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private SeriesEntity series;

}
