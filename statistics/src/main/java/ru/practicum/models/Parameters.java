package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parameters {
    private Long start;
    private Long end;
    private List<String> uris;
    private Boolean unique;
}
