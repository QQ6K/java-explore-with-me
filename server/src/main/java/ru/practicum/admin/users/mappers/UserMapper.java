package ru.practicum.admin.users.mappers;

import org.springframework.stereotype.Component;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.models.User;

@Component
public class UserMapper {
    public User fromCreateDto(UserDto dto) {
        return new User(dto.getId(), dto.getEmail(),dto.getName());
    }

    public UserDto toDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }

}
