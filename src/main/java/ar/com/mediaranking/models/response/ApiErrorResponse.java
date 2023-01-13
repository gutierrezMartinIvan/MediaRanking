package ar.com.mediaranking.models.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
    @Schema(description = "Http Status", example = "Generic http status")
    private HttpStatus status;

    @Schema(description = "Message error", example = "Generic message error")
    private String message;

    @Schema(description = "List of errors", example = "Generic list of errors")
    private List<String> errors;
}
