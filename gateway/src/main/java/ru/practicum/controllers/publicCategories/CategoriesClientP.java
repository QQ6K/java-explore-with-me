package ru.practicum.controllers.publicCategories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
@Service
public class CategoriesClientP extends BaseClient {
    private static final String API_PREFIX = "/categories";

    @Autowired
    public CategoriesClientP(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }
    public ResponseEntity<Object> get(Long catId) {
        return get("/" + catId);
    }

    public ResponseEntity<Object> get(String query, Long id) {
        return get("?" + query);
    }
}
