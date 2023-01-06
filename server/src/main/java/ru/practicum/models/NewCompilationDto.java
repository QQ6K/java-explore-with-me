package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {
    private Collection<Long> events;
    private Boolean pinned;
    @NotBlank(message = "Заголовок подборки пустой")
    private String title;
}
