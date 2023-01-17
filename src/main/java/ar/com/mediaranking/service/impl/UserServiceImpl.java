package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.models.entity.UserEntity;
import ar.com.mediaranking.models.repository.UserRepository;
import ar.com.mediaranking.models.request.UserRequest;
import ar.com.mediaranking.models.response.UserResponse;
import ar.com.mediaranking.service.UserService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DtoToEntityConverter mapper;

    public UserResponse register(UserRequest userRequest) {
        UserEntity userEntity = mapper.convertDtoToEntity(userRequest);

        return mapper.convertEntityToDto(userRepository.save(userEntity));
    }

}
