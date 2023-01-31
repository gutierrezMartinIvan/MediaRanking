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

import java.util.NoSuchElementException;

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
                    @ApiResponse(responseCode = "400", description = "User already exists",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping
    public ResponseEntity<TokenResponse> registerUser(@Valid @RequestBody UserDataRequest userDataRequest) {
        try {
            return new ResponseEntity<>(userService.register(userDataRequest), HttpStatus.CREATED);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Login",
            description = "In this feature you can login with your user"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Logged in successfully!"),
                    @ApiResponse(responseCode = "400", description = "Email or password are incorrect",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

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

    /*@Operation(
            summary = "Grant Admin role to user",
            description = "In this feature you grant admin role to a user by his idd"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Success!"),
                    @ApiResponse(responseCode = "400", description = "User doesn't exist",
                            content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
            }
    )
    @PostMapping("/{id}/admin")
    public void grantAdminById(@PathVariable Long id) {
        userService.grantAdmin(id);
    }*/
}
