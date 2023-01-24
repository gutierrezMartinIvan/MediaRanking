package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class ReviewUpdate {
    @Schema(example = "1")
    private String userId;

    @Schema(example = "This is a review")
    private String review;


    @Schema(description = "Rating must be between 1 and 10", example = "9")
    private Integer rating;
}
