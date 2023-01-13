package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;
    @Column(name = "user_id")
    private String userId;

    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String review;

    @Column(nullable = false)
    @Min(value=1)
    @Max(value=10)
    @NotNull(message = "rating can not be null")
    Integer rating;

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private MovieEntity movies;

    @ManyToOne
    @JoinColumn(name = "series_id")
    private SeriesEntity series;

}
