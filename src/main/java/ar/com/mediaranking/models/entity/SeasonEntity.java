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
    @NotNull(message = "Description can not be null")
    int number;

    @Column(nullable = false)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String Description;

    @ElementCollection
    List<EpisodeEntity> episodes;
    //ReviewEntity review;
}
