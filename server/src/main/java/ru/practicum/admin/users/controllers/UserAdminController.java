package ru.practicum.admin.users.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.users.dto.UserDto;
import ru.practicum.admin.users.interfaces.UserAdminService;

import javax.validation.Valid;

@RestController
@Validated
@RequestMapping("/admin/users")
@RequiredArgsConstructor
public class UserAdminController {
    private final UserAdminService userAdminService;

    @PostMapping
    public UserDto addUser(@Valid @RequestBody UserDto userDto) {
        return userAdminService.addUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public void delete(@PathVariable("userId") Long userId) {
        userAdminService.deleteUser(userId);
    }
}
