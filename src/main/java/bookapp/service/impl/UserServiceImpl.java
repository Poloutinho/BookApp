package bookapp.service.impl;

import bookapp.dto.user.UserRegistrationRequestDto;
import bookapp.dto.user.UserResponseDto;
import bookapp.exception.RegistrationException;
import bookapp.mapper.UserMapper;
import bookapp.model.Role;
import bookapp.model.User;
import bookapp.repository.role.RoleRepository;
import bookapp.repository.user.UserRepository;
import bookapp.service.UserService;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

        User user = new User();
        user.setEmail(requestDto.getEmail());
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRepeatPassword(passwordEncoder.encode(requestDto.getRepeatPassword()));
        user.setFirstName(requestDto.getFirstName());
        user.setLastName(requestDto.getLastName());
        user.setShippingAddress(requestDto.getShippingAddress());
        Set<Role> roles = new HashSet<>();
        for (Role.RoleName roleName : requestDto.getRoles()) {
            Optional<Role> existingRole = roleRepository.findByName(roleName);
            if (existingRole.isPresent()) {
                roles.add(existingRole.get());
            } else {
                Role newRole = new Role();
                roles.add(newRole);
            }
        }
        user.setRoles(roles);
        User savedUser = userRepository.save(user);
        return userMapper.toUserResponse(savedUser);
    }

    @Override
    public UserResponseDto getByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toUserResponse)
                .orElseThrow(() -> new RuntimeException("Can't find user by email:" + email));
    }
}
