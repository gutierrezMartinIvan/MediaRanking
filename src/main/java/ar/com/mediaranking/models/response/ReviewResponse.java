package ar.com.mediaranking.models.response;

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
    private Long id;

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
}
