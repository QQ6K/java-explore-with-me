package ru.practicum.controllers.adminCompilations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.models.NewCompilationDto;

@Service
public class CompilationsClientA extends BaseClient {

    private static final String API_PREFIX = "/admin/compilations";

    @Autowired
    public CompilationsClientA(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> create(NewCompilationDto newCompilationDto) {
        return post("", newCompilationDto);
    }

    public ResponseEntity<Object> delete(Long compId) {
        return delete("/" + compId);
    }

    public ResponseEntity<Object> delete(Long compId, Long eventId) {
        return delete("/" + compId + "/events" + "/" + eventId);
    }

    public ResponseEntity<Object> delete(Long compId, String pin) {
        return delete("/" + compId + "/" + pin);
    }

    public ResponseEntity<Object> patch(Long compId, Long eventId) {
        return patch("/" + compId + "/events" + "/" + eventId);
    }

    public ResponseEntity<Object> patch(Long compId, String pin) {
        return patch("/" + compId + "/" + pin);
    }

}
