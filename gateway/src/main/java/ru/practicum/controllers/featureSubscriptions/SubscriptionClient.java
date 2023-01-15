package ru.practicum.controllers.featureSubscriptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;

@Service
public class SubscriptionClient extends BaseClient {
    private static final String API_PREFIX = "/feed";

    @Autowired
    public SubscriptionClient(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> get(Long userId, String query) {
        return get("/" + userId + "?" + query);
    }

    public ResponseEntity<Object> post(Long userId, Long subscriptionUserId) {
        return post("/" + userId + "/subscription/" + subscriptionUserId);
    }

    public ResponseEntity<Object> delete(Long userId, Long subscriptionUserId) {
        return delete("/" + userId + "/subscription/" + subscriptionUserId);
    }

    public ResponseEntity<Object> get(Long userId) {
        return get("/" + userId + "/subscription");
    }

    public ResponseEntity<Object> post(Long userId) {
        return post("/" + userId + "/subscription/lock");
    }

    public ResponseEntity<Object> delete(Long userId) {
        return delete("/" + userId + "/subscription/unlock");
    }


}
