package ru.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.models.EndpointHit;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT  COUNT (DISTINCT h.ip)  FROM EndpointHit h" +
            " WHERE h.uri IN (:uris) AND h.timestamp >= :start AND h.timestamp <= :end " +
            "GROUP BY h.ip")
    Long getHitCountUnique(Long start, Long end, String uris);

    @Query("SELECT COUNT (h.id) FROM EndpointHit h " +
            "WHERE h.uri IN (:uris) AND h.timestamp >= :start AND h.timestamp <= :end")
    Long getHitCountAll(Long start, Long end, String uris);
}
