package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Pageable;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SearchParameters {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private Long userId;
    private Long[] feedSubs;
    private Long[] catId;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private Pageable pageable;
    private Boolean paid;
    private Boolean onlyAvailable;

    public void setRangeStart(String rangeStartString) {
        if (rangeStartString != null) {
            this.rangeStart = LocalDateTime.parse(URLDecoder.decode(rangeStartString, StandardCharsets.UTF_8), formatter);
        } else this.rangeStart = null;
    }

    public void setRangeEnd(String rangeEndString) {
        if (rangeEndString != null) {
            this.rangeStart = LocalDateTime.parse(URLDecoder.decode(rangeEndString, StandardCharsets.UTF_8), formatter);
        } else this.rangeStart = null;
    }
}
