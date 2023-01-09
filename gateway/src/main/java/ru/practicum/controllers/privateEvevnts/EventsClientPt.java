package ru.practicum.controllers.privateEvevnts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.models.NewEventDto;
import ru.practicum.models.UpdateEventRequest;

@Service
public class EventsClientPt extends BaseClient {

    private static final String API_PREFIX = "/users";

    @Autowired
    public EventsClientPt(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> get(String uri, Long id) {
        return get("/" + id + "/events?" + uri);
    }

    public ResponseEntity<Object> patch(UpdateEventRequest updateEventRequest, Long userId) {
        return patch("/" + userId + "/events", updateEventRequest);
    }

    public ResponseEntity<Object> post(NewEventDto newEventDto, Long userId) {
        return post("/" + userId + "/events", newEventDto);
    }

    public ResponseEntity<Object> get(Long userId, Long eventId) {
        return get("/" + userId + "/events/" + eventId);
    }

    public ResponseEntity<Object> get(Long userId, Long eventId, String request) {
        return get("/" + userId + "/events/" + eventId + "/" + request);
    }

    public ResponseEntity<Object> patch(Long userId, Long eventId) {
        return patch("/" + userId + "/events/" + eventId);
    }

    public ResponseEntity<Object> patch(Long userId, Long eventId, Long reqId, String state) {
        return patch("/" + userId + "/events/" + eventId + "/requests/" + reqId + "/" + state);
    }

}
