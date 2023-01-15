package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchParameters {
    private Long userId;
    private Long[] feedSubs;
    private Long[] catId;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Pageable pageable;
    private Boolean paid;
    private Boolean onlyAvailable;
}