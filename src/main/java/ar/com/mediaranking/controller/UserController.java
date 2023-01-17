package ar.com.mediaranking.controller;

import ar.com.mediaranking.models.request.UserRequest;
import ar.com.mediaranking.models.response.UserResponse;
import ar.com.mediaranking.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.register(userRequest), HttpStatus.CREATED);
    }
}
