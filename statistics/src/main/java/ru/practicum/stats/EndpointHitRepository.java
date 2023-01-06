package ru.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.models.EndpointHit;

import java.time.LocalDateTime;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT COUNT (DISTINCT h.ip) FROM EndpointHit h" +
            " WHERE h.uri = :uris AND h.timestamp > :start AND h.timestamp < :end " +
            "GROUP BY h.ip")
    Long getHitCountUnique(LocalDateTime start, LocalDateTime end, String uris);

    @Query("SELECT COUNT (h.id) FROM EndpointHit h " +
            "WHERE h.uri = :uris AND h.timestamp > :start AND h.timestamp < :end")
    Long getHitCountAll(LocalDateTime start, LocalDateTime end, String uris);
}
