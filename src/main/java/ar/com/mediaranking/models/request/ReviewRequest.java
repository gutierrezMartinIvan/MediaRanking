package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    private Long entityId;

    @NotEmpty(message = "User ID can not be empty")
    @Schema(example = "1")
    private String userId;
    @NotBlank(message = "review can not be blank")
    @Schema(example = "This is a review")
    private String review;
    @Schema(description = "Rating must be between 1 and 10", example = "9")
    @NotNull(message = "rating can not be null")
    private Integer rating;
}
