package ru.practicum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.enums.State;
import ru.practicum.models.ParticipationRequest;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {

    List<ParticipationRequest> findByEventIdAndState(Long id, State state);

    List<ParticipationRequest> findByRequesterId(Long id);

    List<ParticipationRequest> findByEventInitiatorIdAndEventId(Long initiatorId, Long eventId);

    ParticipationRequest findByEvent_IdAndRequesterId(Long eventId, Long userId);


}
