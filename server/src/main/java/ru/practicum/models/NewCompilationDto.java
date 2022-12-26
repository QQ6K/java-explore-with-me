package ru.practicum.models;

import javax.validation.constraints.NotBlank;
import java.util.Collection;

public class NewCompilationDto {
    private Collection<Long> events;
    private Boolean pinned;
    @NotBlank(message = "Заголовок подборки пустой")
    private String title;
}
