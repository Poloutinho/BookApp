package com.example.bookapp.src.security;

import com.example.bookapp.src.dto.user.UserLoginRequestDto;
import com.example.bookapp.src.model.User;
import com.example.bookapp.src.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;

    public boolean authenticate(UserLoginRequestDto requestDto) {
        Optional<User> user = userRepository.findByEmail(requestDto.email());
        return user.isPresent() && user.get().getPassword().equals(requestDto.password());
    }
}
