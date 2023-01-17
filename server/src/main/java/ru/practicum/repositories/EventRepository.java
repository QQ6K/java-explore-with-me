package ru.practicum.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.enums.State;
import ru.practicum.models.Event;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByInitiatorIdOrderByEventDateDesc(Long id, Pageable pageable);

    List<Event> findByIdIn(List<Long> ids);

    @Query(value = "SELECT e FROM Event e WHERE e.initiator.id IN :initiatorId AND e.category.id IN :categoryId " +
            "AND e.state IN :states AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd")
    Page<Event> findAllEventsAdmin(Long[] initiatorId, Long[] categoryId, String[] states,
                                   LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable);

    @Query(value = "SELECT e FROM Event e WHERE e.initiator.id IN :initiatorId AND e.category.id IN :categoryId " +
            "AND e.state IN :states")
    Page<Event> findAllEventsAdminNotRange(Long[] initiatorId, Long[] categoryId, List<State> states, Pageable pageable);

    @Query(value = "SELECT e FROM Event e WHERE e.initiator.id IN :initiatorId AND e.category.id IN :categoryId ")
    Page<Event> findAllEventsAdminNotRangeWithoutStates(Long[] initiatorId, Long[] categoryId, Pageable pageable);

    @Query(value = "SELECT e FROM Event e WHERE (LOWER(e.description) LIKE LOWER(CONCAT('%',:text,'%')) OR " +
            "LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(e.title) LIKE LOWER(CONCAT('%', :text, '%')))" +
            "AND e.category.id IN :catIds AND e.paid = :paid AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd")
    Page<Event> findAllForPublic(String text, Long[] catIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                 Pageable pageable);

    @Query(value = "SELECT e FROM Event e WHERE (LOWER(e.description) LIKE LOWER(CONCAT('%',:text,'%')) OR " +
            "LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR LOWER(e.title) LIKE LOWER(CONCAT('%', :text, '%')))" +
            "AND e.category.id IN :catIds AND e.paid = :paid AND e.eventDate > :rangeStart AND e.eventDate < :rangeEnd" +
            " AND size(e.participants) < e.participantLimit")
    Page<Event> findAllForPublicAvailable(String text, Long[] catIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Pageable pageable);

    List<Event> findByCategoryId(Long catId);

    @Query(value = "SELECT e FROM Event e WHERE " +
            "(:catIds is null or e.category.id IN :catIds) " +
            "AND (:paid is null or e.paid = :paid) " +
            "AND ((cast(:rangeStart as timestamp ) is null) or e.eventDate > :rangeStart)" +
            "AND ((cast(:rangeEnd as timestamp ) is null) or e.eventDate < :rangeEnd)" +
            " AND size(e.participants) < e.participantLimit " +
            "AND e.initiator.id IN :userId")
    Page<Event> findAllForPublicAvailableUserSubscription(Long[] userId, Long[] catIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                          Pageable pageable);

    @Query(value = "SELECT e FROM Event e WHERE " +
            "(:catIds is null or e.category.id IN :catIds) " +
            "AND (:paid is null or e.paid = :paid) " +
            "AND ((cast(:rangeStart as timestamp ) is null) or e.eventDate > :rangeStart)" +
            "AND ((cast(:rangeEnd as timestamp ) is null) or e.eventDate < :rangeEnd)" +
            " AND size(e.participants) < e.participantLimit " +
            "AND e.initiator.id IN :userId")
    Page<Event> findAllForPublicUserSubscription(Long[] userId, Long[] catIds, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                              Pageable pageable);
}
