package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "movies")
@Data
@Builder
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

    @Column(nullable = false)
    String director;

    @ElementCollection
    @CollectionTable(name = "generes", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "generes")
    private Set<String> genres = new HashSet<>();

    @Column(nullable = false)
    @Min(value=1)
    @Max(value=9999)
    int  duration;

    /*
    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    List<ReviewEntity> reviews;
    */
}
