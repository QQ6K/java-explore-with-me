package ru.practicum.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    Long id;
    String app;
    String uri;
    String ip;
    String timestamp;
}
