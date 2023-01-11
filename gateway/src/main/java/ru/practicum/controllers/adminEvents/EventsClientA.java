package ru.practicum.controllers.adminEvents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.models.AdminUpdateEventRequest;

@Service
public class EventsClientA extends BaseClient {

    private static final String API_PREFIX = "/admin/events";

    @Autowired
    public EventsClientA(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> put(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        return put("/" + eventId, adminUpdateEventRequest);
    }

    public ResponseEntity<Object> patch(Long eventId, String state) {
        return patch("/" + eventId + "/" + state);
    }

    public ResponseEntity<Object> get(String query, Long id) {
        return get("?" + query);
    }

}
