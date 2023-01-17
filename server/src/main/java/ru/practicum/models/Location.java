package ru.practicum.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "locations")
@Getter
@Setter
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
