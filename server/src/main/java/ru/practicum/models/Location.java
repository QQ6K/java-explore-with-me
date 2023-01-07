package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "locations")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotNull(message = "Пустое поле 'lat'")
    Float lat;
    @NotNull(message = "Пустое поле 'lon'")
    Float lon;
}
