package ar.com.mediaranking.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Email")
    private String email;

    @Schema(example = "First Name")
    private String firstName;

    @Schema(example = "Last Name")
    private String lastName;

}
