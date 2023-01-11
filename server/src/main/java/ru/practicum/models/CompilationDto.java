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
    @NotNull(message = "id не должно быть null")
    private Long id;
    @NotBlank(message = "Пустой заголовок")
    private String title;
    @NotNull(message = "Значение 'Закреплено' не определено")
    private Boolean pinned;
    private Collection<EventShortDto> events;

}
