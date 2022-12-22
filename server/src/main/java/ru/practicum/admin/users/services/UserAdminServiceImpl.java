package ru.practicum.admin.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.users.interfaces.UserAdminService;
import ru.practicum.admin.users.mappers.UserMapper;
import ru.practicum.admin.users.repositories.UserAdminRepository;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.models.User;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {
    private final UserAdminRepository repository;
    private final UserMapper userDtoMapper;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = repository.save(userDtoMapper.fromCreateDto(userDto));
        log.info("Создание пользователя с id = {}", user.getId());
        return userDtoMapper.toDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Удаление пользователя с id = {}", userId);
        repository.deleteById(userId);
    }
}
