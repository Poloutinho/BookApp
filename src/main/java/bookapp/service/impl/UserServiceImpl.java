package bookapp.service.impl;

import bookapp.dto.user.UserRegistrationRequestDto;
import bookapp.dto.user.UserResponseDto;
import bookapp.exception.EntityNotFoundException;
import bookapp.exception.RegistrationException;
import bookapp.mapper.UserMapper;
import bookapp.model.Role;
import bookapp.model.User;
import bookapp.repository.role.RoleRepository;
import bookapp.repository.user.UserRepository;
import bookapp.service.UserService;
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
            throw new RegistrationException("User with this email already exists");
        }

        User user = mapToUser(requestDto);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    private User mapToUser(UserRegistrationRequestDto requestDto) {
        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        Set<Role> roles = requestDto.getRoles().stream()
                .map(this::getRole)
                .collect(Collectors.toSet());
        user.setRoles(roles);
        return user;
    }

    private Role getRole(Role.RoleName roleName) {
        return roleRepository.findByName(roleName).get();
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponse)
                .orElseThrow(() ->
                        new EntityNotFoundException("Can't find user by email: " + email));
    }
}
