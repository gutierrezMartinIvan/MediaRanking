package ar.com.mediaranking.models.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserRequest {

    @Schema(example = "Email")
    private String email;

    @Schema(example = "Password")
    private String password;

    @Schema(example = "First Name")
    private String firstName;

    @Schema(example = "Last Name")
    private String lastName;

}
