package com.example.bookapp.src.service;

import com.example.bookapp.src.dto.user.UserRegistrationRequestDto;
import com.example.bookapp.src.dto.user.UserResponseDto;
import com.example.bookapp.src.exception.RegistrationException;
import com.example.bookapp.src.mapper.UserMapper;
import com.example.bookapp.src.model.Role;
import com.example.bookapp.src.model.User;
import com.example.bookapp.src.repository.role.RoleRepository;
import com.example.bookapp.src.repository.user.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can`t register user");
        }

        Set<User.RoleName> roleNames = requestDto.getRoles();
        for (User.RoleName roleName : roleNames) {
            Optional<Role> existingRole = roleRepository.findByName(roleName);
            if (existingRole.isPresent()) {
                User user = new User();
                user.setEmail(requestDto.getEmail());
                user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
                user.setRepeatPassword(passwordEncoder.encode(requestDto.getRepeatPassword()));
                user.setFirstName(requestDto.getFirstName());
                user.setLastName(requestDto.getLastName());
                user.setShippingAddress(requestDto.getShippingAddress());
                User savedUser = userRepository.save(user);
                return userMapper.toUserResponse(savedUser);
            }
        }
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRepeatPassword(passwordEncoder.encode(requestDto.getRepeatPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        user.setRoles(requestDto.getRoles().stream()
                .map(roleName -> Role.builder().name(roleName).build())
                .collect(Collectors.toSet()));
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }
}
