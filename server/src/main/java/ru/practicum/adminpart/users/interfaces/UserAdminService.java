package ru.practicum.adminpart.users.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.UserDto;

import java.util.Collection;

public interface UserAdminService {

    UserDto createUser(UserDto userDto);

    void deleteUser(Long userId);

    Collection<UserDto> findUsers(Long[] ids, Pageable pageable);
}
