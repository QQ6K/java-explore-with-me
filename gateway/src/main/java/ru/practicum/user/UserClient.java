package ru.practicum.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;

@Service
public class UserClient extends BaseClient {
    private static final String API_PREFIX = "/admin/users";

    @Autowired
    public UserClient(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> create(UserDto userDto) {
        return post("", userDto);
    }

    public ResponseEntity<Object> delete(Long userId) {
        return delete("/" + userId);
    }

    public ResponseEntity<Object> get(Long[] ids, Long from, Long size) {
        String path = null;
        if (ids != null) {
            path = "?ids=" + ids;
        }
        if (size != null) {
            path += "&from=" + from;
        }
        if (size != null) {
            path += "&size=" + size;
        }
        return get(path, null);
    }
}
