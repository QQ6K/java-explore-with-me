package ru.practicum.admin.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.admin.users.models.User;

import java.util.Optional;

@Repository
public interface UserAdminRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);
}
