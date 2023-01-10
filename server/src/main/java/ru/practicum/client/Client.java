package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.practicum.models.EndpointHitDto;

@Service
@Slf4j
public class Client extends ClientForStat {
    private static final String saveHitEndpoint = "/hit";

    public Client(@Value("${explore-with-me-stat.url}") String baseUrl) {
        super(baseUrl);
    }

    public void post(EndpointHitDto endpointHitDto) {
        ResponseEntity<Object> response = super.post(saveHitEndpoint, endpointHitDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Сбор статистики: {}", response);
        } else {
            log.error("Ошибка : {}", response.getStatusCodeValue());
        }
    }
}
