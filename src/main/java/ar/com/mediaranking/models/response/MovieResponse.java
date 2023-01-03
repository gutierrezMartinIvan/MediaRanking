package ar.com.mediaranking.models.response;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class MovieResponse {
    Long id;
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String title;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String description;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String director;

    List<String> genres;

    @Min(value=1)
    @Max(value=9999)
    int  duration;
}
