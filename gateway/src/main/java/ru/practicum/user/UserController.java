package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Validated
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserClient userClient;

    @ResponseBody
    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserDto userDto) {
        log.info("Запрос 'POST /admin/users'");
        return userClient.create(userDto);
    }

    @ResponseBody
    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable Long userId) {
        log.info("Запрос 'DELETE /admin/users'");
        return userClient.delete(userId);
    }
}
