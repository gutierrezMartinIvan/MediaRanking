package ar.com.mediaranking.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {
    @Schema(example = "1")
    private Long id;

    @Schema(example = "1")
    private String userId;

    @Schema(example = "This is a review")
    private String review;

    @Schema(description = "Rating must be between 1 and 10", example = "9")
    private Integer rating;
}
