package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {
    private Collection<EventShortDto> events;
    @NotNull(message = "id не должно быть null")
    private Long id;
    @NotBlank(message = "Значение 'Закреплено' не определено")
    private Boolean pinned;
    @NotBlank(message = "Пустой заголовок")
    private String title;
}
