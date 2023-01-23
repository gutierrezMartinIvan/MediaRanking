package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.config.security.JwtService;
import ar.com.mediaranking.exception.AlreadyExistsException;
import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.Role;
import ar.com.mediaranking.models.entity.UserEntity;
import ar.com.mediaranking.models.repository.UserRepository;
import ar.com.mediaranking.models.request.LoginRequest;
import ar.com.mediaranking.models.request.UserDataRequest;
import ar.com.mediaranking.models.response.TokenResponse;
import ar.com.mediaranking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.rmi.AlreadyBoundException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    @Lazy
    private JwtService jwtService;
    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public TokenResponse register(UserDataRequest userDataRequest) throws AlreadyExistsException {

        if(userRepository.findByEmail(userDataRequest.getEmail()).isPresent()) {
            throw new AlreadyExistsException("A user with that email already exists");
        }

        UserEntity user = UserEntity
                .builder()
                .email(userDataRequest.getEmail())
                .password(passwordEncoder.encode(userDataRequest.getPassword()))
                .firstName(userDataRequest.getFirstName())
                .lastName(userDataRequest.getLastName())
                .role(Role.USER)
                .build();

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);
        return TokenResponse.builder().token(jwt).build();
    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) throws AuthenticationException, NoSuchElementException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        UserEntity user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow();

        var jwt = jwtService.generateToken(user);
        return TokenResponse.builder().token(jwt).build();
    }

    @Override
    public void deleteUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if(userOptional.isEmpty())
            throw new NotFoundException("There is not a user with the id: " + id);
        userRepository.deleteById(id);
    }

    @Override
    public void update(Long id, UserDataRequest userDataRequest) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id: " + id + " not found"));

        if(userDataRequest.getEmail() != null) {
            user.setFirstName(userDataRequest.getEmail());
        }
        if(userDataRequest.getPassword() != null) {
            user.setFirstName(userDataRequest.getPassword());
        }
        if(userDataRequest.getLastName() != null) {
            user.setFirstName(userDataRequest.getLastName());
        }
        if(userDataRequest.getFirstName() != null) {
            user.setFirstName(userDataRequest.getFirstName());
        }
    }

    public Optional<UserEntity> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


}
