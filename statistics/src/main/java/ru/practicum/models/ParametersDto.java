package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametersDto {
        private String start;
        private String end;
        private String[] uris;
        private Boolean unique;
}
