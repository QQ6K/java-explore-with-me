package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Location {

    Long id;
    @NotNull(message = "Пустое поле 'lat'")
    Float lat;
    @NotNull(message = "Пустое поле 'lon'")
    Float lon;
}
