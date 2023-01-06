package ru.practicum.privatepart.participationRequest.interfaces;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.models.ParticipationRequest;
import ru.practicum.models.ParticipationRequestDto;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestService {
    ParticipationRequest findByRequestId(Long requestId);

    List<ParticipationRequestDto> findAllByParticipantId(Long userId);

    @Transactional
    ParticipationRequestDto createParticipation(Long userId, Optional<Long> eventId);

    @Transactional
    ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId);
}
