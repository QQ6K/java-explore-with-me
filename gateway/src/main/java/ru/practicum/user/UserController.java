package ru.practicum.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.exceptions.BadRequestException;

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

    @GetMapping
    public ResponseEntity<Object> findAll(@RequestParam(name = "from", defaultValue = "0") Long from,
                                          @RequestParam(name = "size", defaultValue = "10") Long size,
                                          @RequestParam(value = "ids", required = false) Long[] ids) {
        if (size <= 0 || from < 0) {
            throw new BadRequestException("Ошибка параметров пагинации");
        }
        log.info("Запрос ");
        return userClient.get(ids, from, size);
    }
}
