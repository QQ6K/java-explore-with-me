package ru.practicum.client;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ClientForStat {
    private final WebClient webClient;

    public ClientForStat(String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    protected ResponseEntity<Object> post(String path, Object object) {
        return webClient
                .post()
                .uri(path)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(object), Object.class)
                .retrieve()
                .toEntity(Object.class)
                .onErrorResume(e -> Mono.error(new RuntimeException("Statistic server error")))
                .block();


    }
}
