package ar.com.mediaranking.service;

import ar.com.mediaranking.models.entity.UserEntity;
import ar.com.mediaranking.models.request.UserRequest;
import ar.com.mediaranking.models.request.UserUpdate;
import ar.com.mediaranking.models.response.UserResponse;

import java.util.Optional;

public interface UserService {

    UserResponse register(UserRequest userRequest);

    void deleteUserById(Long id);

    UserResponse update(Long id, UserUpdate UserRequest);

    Optional<UserEntity> findUserByEmail(String email);

}
