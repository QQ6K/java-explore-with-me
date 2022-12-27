package ru.practicum.adminpart.users.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAdminRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userId);

    @Query("SELECT u FROM User u WHERE u.id IN (:ids)")
    Page<User> findAllById(List<Long> ids, Pageable pageable);
}
