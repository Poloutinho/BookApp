package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.user.UserRegistrationRequestDto;
import com.example.bookapp.src.dto.user.UserResponseDto;
import com.example.bookapp.src.exception.RegistrationException;
import com.example.bookapp.src.mapper.UserMapper;
import com.example.bookapp.src.model.User;
import com.example.bookapp.src.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can`t register user");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setRepeatPassword(requestDto.getPassword());
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
