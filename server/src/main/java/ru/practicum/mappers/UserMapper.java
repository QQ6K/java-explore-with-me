package ru.practicum.mappers;

import ru.practicum.models.User;
import ru.practicum.models.UserDto;
import ru.practicum.models.UserShortDto;

public class UserMapper {
    public static UserShortDto toShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static User fromUserDto(UserDto dto) {
        return new User(dto.getId(), dto.getEmail(),dto.getName());
    }

    public static UserDto toUserDto(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getName());
    }
}
