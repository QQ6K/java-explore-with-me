package ru.practicum.stats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.models.EndpointHit;

@Repository
public interface EndpointHitRepository extends JpaRepository<EndpointHit, Long> {
    @Query("SELECT  COUNT (DISTINCT hit.ip)  FROM EndpointHit hit" +
            " WHERE hit.uri = :uris AND hit.timestamp > :start AND hit.timestamp < :end " +
            "GROUP BY hit.ip")
    Long getHitCountUnique(Long start, Long end, String uris);

    @Query("SELECT COUNT (hit.id) FROM EndpointHit hit " +
            "WHERE hit.uri = :uris AND hit.timestamp > :start AND hit.timestamp < :end")
    Long getHitCountAll(Long start, Long end, String uris);
}
