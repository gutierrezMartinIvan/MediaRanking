package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
@Entity
@Table(name = "episodes")
@Data
public class EpisodeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episodes_id")
    Long id;

    @Column(nullable = false)
    @NotNull(message = "Tittle can not be null")
    @NotBlank(message = "Tittle can not be blank")
    @NotEmpty(message = "Tittle can not be empty")
    @Pattern(regexp =  "^[A-Za-z0-9!@#$%^&*()_+=-]*$", message = "Tittle only can contain letters, numbers and special characters")
    String tittle;

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
    /*
    @ManyToOne
    @JoinColumn(name = "season_entity_season_id")
    private SeasonEntity seasonEntity;*/

}
