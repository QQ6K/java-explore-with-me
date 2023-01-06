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
        return userAdminService.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        userAdminService.deleteUser(userId);
    }

    @GetMapping
    public Collection<UserDto> findAllUsers(@RequestParam(name = "from") Integer from,
                                       @RequestParam(name = "size") Integer size,
                                       @RequestParam(value = "ids", required = false) Long[] ids) {
        Pageable pageable;
        if (size == null || from == null) {
            pageable = Pageable.unpaged();
        } else {
            int page = from / size;
            pageable = PageRequest.of(page, size);
        }
        return userAdminService.findUsers(ids, pageable);
    }
}



