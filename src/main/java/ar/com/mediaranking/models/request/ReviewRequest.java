package ar.com.mediaranking.models.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @NotNull(message = "User ID can not be null")
    @NotBlank(message = "User ID can not be blank")
    @NotEmpty(message = "User ID can not be empty")
    private String userId;
    @NotNull(message = "review can not be null")
    @NotBlank(message = "review can not be blank")
    @NotEmpty(message = "review can not be empty")
    private String review;

    @NotNull(message = "Rating can not be null")
    @NotBlank(message = "Rating can not be blank")
    @NotEmpty(message = "Rating can not be empty")
    private String rating;

    @NotNull(message = "series Id can not be null")
    @NotBlank(message = "series Id can not be blank")
    @NotEmpty(message = "series Id can not be empty")
    private Long seriesId;
}
