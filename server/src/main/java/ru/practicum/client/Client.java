package ru.practicum.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Client extends ClientForStat {
    final static private String saveHitEndpoint = "/hit";

    public Client(@Value("${explore-with-me-stat.url}") String baseUrl) {
        super(baseUrl);
    }


    public void post(EndpointHit endpointHitDto) {
        ResponseEntity<Object> response = super.post(saveHitEndpoint, endpointHitDto);
        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("Statistic saved: {}", response);
        } else {
            log.error("Error statistic server Http code: {}", response.getStatusCodeValue());
        }
    }
}
