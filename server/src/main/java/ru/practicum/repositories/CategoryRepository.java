package ru.practicum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.models.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
