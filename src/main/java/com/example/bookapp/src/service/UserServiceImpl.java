package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.user.UserRegistrationRequestDto;
import com.example.bookapp.src.dto.user.UserResponseDto;
import com.example.bookapp.src.exception.RegistrationException;
import com.example.bookapp.src.mapper.UserMapper;
import com.example.bookapp.src.model.Role;
import com.example.bookapp.src.model.User;
import com.example.bookapp.src.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can`t register user");
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRepeatPassword(passwordEncoder.encode(requestDto.getRepeatPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        user.setRoles(requestDto.getRoles().stream().map(roleName -> Role.builder().name(roleName).build())
                .collect(Collectors.toSet()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
