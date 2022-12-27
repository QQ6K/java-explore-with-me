package ru.practicum.adminpart.users.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.adminpart.users.interfaces.UserAdminService;
import ru.practicum.adminpart.users.repositories.UserAdminRepository;
import ru.practicum.mappers.UserMapper;
import ru.practicum.models.User;
import ru.practicum.models.UserDto;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {
    private final UserAdminRepository userAdminRepository;

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = userAdminRepository.save(UserMapper.fromUserDto(userDto));
        log.info("Создание пользователя с id = {}", user.getId());
        return UserMapper.toUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        log.info("Удаление пользователя с id = {}", userId);
        userAdminRepository.deleteById(userId);
    }

    @Override
    public Collection<UserDto> findUsers(Long[] ids, Pageable pageable) {
        if (ids != null) {
            log.info("Поиск пользователей с id = {}" + ids);
            return userAdminRepository.findAllById(Arrays.asList(ids), pageable).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            log.info("Поиск всех пользователей");
            return userAdminRepository.findAll(pageable).stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());}
    }

}
