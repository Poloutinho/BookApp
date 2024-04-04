package com.example.bookapp.src.mapper;

import com.example.bookapp.src.config.MapperConfig;
import com.example.bookapp.src.dto.user.UserResponseDto;
import com.example.bookapp.src.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface UserMapper {

    UserResponseDto toUserResponse(User savedUser);

    @Named("userFromId")
    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
