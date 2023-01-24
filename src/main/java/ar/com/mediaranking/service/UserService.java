package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.UserEntity;
import ar.com.mediaranking.models.request.LoginRequest;
import ar.com.mediaranking.models.request.UserDataRequest;
import ar.com.mediaranking.models.response.TokenResponse;

import java.util.Optional;

public interface UserService {

    TokenResponse register(UserDataRequest userDataRequest);

    TokenResponse login(LoginRequest loginRequest);

    void deleteUserById(Long id);

    void update(Long id, UserDataRequest userDataRequest);

    Optional<UserEntity> findUserByEmail(String email);

}
