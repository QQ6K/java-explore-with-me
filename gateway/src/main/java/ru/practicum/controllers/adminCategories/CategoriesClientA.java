package ru.practicum.controllers.adminCategories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.client.BaseClient;
import ru.practicum.models.CategoryDto;
import ru.practicum.models.NewCategoryDto;

@Service
public class CategoriesClientA extends BaseClient {
    private static final String API_PREFIX = "/admin/categories";

    @Autowired
    public CategoriesClientA(@Value("${explore-with-me-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> create(NewCategoryDto newCategoryDto) {
        return post("", newCategoryDto);
    }

    public ResponseEntity<Object> delete(Long categoryId) {
        return delete("/" + categoryId);
    }

    public ResponseEntity<Object> patch(CategoryDto categoryDto) {
        return patch("", categoryDto);
    }
}
