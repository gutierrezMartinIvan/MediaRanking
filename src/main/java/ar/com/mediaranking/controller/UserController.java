package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.LoginRequest;
import ar.com.mediaranking.models.request.UserDataRequest;
import ar.com.mediaranking.models.response.ApiErrorResponse;
import ar.com.mediaranking.models.response.TokenResponse;
import ar.com.mediaranking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(
        name = "User functions",
        description = "Here you can use all the provides features for users, like register, login, etc."
)
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(
            summary = "Register user",
            description = "In this feature you can register a new user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "User created successfully!"),
            }
    )
    @PutMapping
    public ResponseEntity<TokenResponse> registerUser(@Valid @RequestBody UserDataRequest userDataRequest) {
        return new ResponseEntity<>(userService.register(userDataRequest), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Login",
            description = "In this feature you can login with your user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Login successfully!"),
            }
    )
    @PostMapping
    public ResponseEntity<TokenResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @Operation(
            summary = "Delete user by ID",
            description = "In this feature you can delete a user by ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User deleted successfully!"),
                    @ApiResponse(responseCode = "404", description = "User not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @DeleteMapping("/{id}")
    public void deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    @Operation(
            summary = "Update user by ID",
            description = "In this feature you can update a user information by ID"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "User updated successfully!"),
                    @ApiResponse(responseCode = "404", description = "User not found!",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping("/{id}")
    public void updateUserById(@PathVariable Long id, @Valid @RequestBody UserDataRequest userDataRequest) {
        userService.update(id, userDataRequest);
    }
}
