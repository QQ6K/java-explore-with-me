package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameters {
    LocalDateTime start;
    LocalDateTime end;
    List<String> uris;
    Boolean unique;
}
