package ar.com.mediaranking.models.response;

import jakarta.validation.constraints.*;

public class SeriesResponse {

    Long id;
    @NotNull(message = "Tittle can not be null")
    @NotBlank(message = "Tittle can not be blank")
    @NotEmpty(message = "Tittle can not be empty")
    @Pattern(regexp =  "^[A-Za-z0-9!@#$%^&*()_+=-]*$", message = "Tittle only can contain letters, numbers and special characters")
    String title;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String description;

    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    String author;

    @Min(1800)
    @Max(2023)
    @NotNull(message = "Description can not be null")
    @NotBlank(message = "Description can not be blank")
    @NotEmpty(message = "Description can not be empty")
    Short year;
}
