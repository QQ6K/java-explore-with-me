package ru.practicum.adminpart.users.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.adminpart.users.interfaces.UserAdminService;
import ru.practicum.models.UserDto;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Validated
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PostMapping
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        log.info("Запрос POST /admin/users");
        return userAdminService.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable Long userId) {
        log.info("Запрос DELETE /admin/users/{}", userId);
        userAdminService.deleteUser(userId);
    }

    @GetMapping
    public Collection<UserDto> findAllUsers(@RequestParam(name = "from", defaultValue = "0") Integer from,
                                       @RequestParam(name = "size", defaultValue = "10") Integer size,
                                       @RequestParam(value = "ids", required = false) Long[] ids) {
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        log.info("Запрос GET /admin/users c параметрами from = {}, size = {}, ids = {}", from, size, ids);
        return userAdminService.findUsers(ids, pageable);
    }
}



