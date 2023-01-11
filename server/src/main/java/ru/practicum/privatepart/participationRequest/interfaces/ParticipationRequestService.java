package ru.practicum.privatepart.participationRequest.interfaces;

import ru.practicum.models.ParticipationRequest;
import ru.practicum.models.ParticipationRequestDto;

import java.util.List;
import java.util.Optional;

public interface ParticipationRequestService {
    ParticipationRequest findByRequestId(Long requestId);

    List<ParticipationRequestDto> findAllByParticipantId(Long userId);

    ParticipationRequestDto createParticipation(Long userId, Optional<Long> eventId);

    ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId);
}
