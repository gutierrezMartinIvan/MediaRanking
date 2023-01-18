package ar.com.mediaranking.service.impl;

import ar.com.mediaranking.exception.NotFoundException;
import ar.com.mediaranking.models.entity.SeriesEntity;
import ar.com.mediaranking.models.entity.UserEntity;
import ar.com.mediaranking.models.repository.UserRepository;
import ar.com.mediaranking.models.request.UserRequest;
import ar.com.mediaranking.models.response.UserResponse;
import ar.com.mediaranking.service.MovieService;
import ar.com.mediaranking.service.UserService;
import ar.com.mediaranking.utils.DtoToEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public void deleteUserById(Long id) {
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent())
            throw new NotFoundException("There is not a user with the id: " + id);
        userRepository.deleteById(id);
    }


}
