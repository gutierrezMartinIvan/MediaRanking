package ar.com.mediaranking.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="email", nullable = false)
    @NotNull(message = "Email can not be null")
    @NotBlank(message = "Email can not be blank")
    @NotEmpty(message = "Email can not be empty")
    private String email;

    @Column(name = "password", nullable = false)
    @NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be blank")
    @NotEmpty(message = "Password can not be empty")
    private String password;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "First name can not be null")
    @NotBlank(message = "First name can not be blank")
    @NotEmpty(message = "First name can not be empty")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "Last name can not be null")
    @NotBlank(message = "Last name can not be blank")
    @NotEmpty(message = "Last name can not be empty")
    private String lastName;

}
