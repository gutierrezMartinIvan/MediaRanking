package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "1")
    private String userId;
    @NotNull(message = "review can not be null")
    @NotBlank(message = "review can not be blank")
    @NotEmpty(message = "review can not be empty")
    @Schema(example = "This is a review")
    private String review;

    @NotNull(message = "Rating can not be null")
    @NotBlank(message = "Rating can not be blank")
    @NotEmpty(message = "Rating can not be empty")
    @Schema(description = "Rating must be between 1 and 10", example = "9")
    private Integer rating;
}
