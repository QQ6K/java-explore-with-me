package ru.practicum.admin.users.interfaces;

import ru.practicum.admin.users.dto.UserDto;

public interface UserAdminService {
    UserDto addUser(UserDto userDto);

    void deleteUser(Long userId);

}
