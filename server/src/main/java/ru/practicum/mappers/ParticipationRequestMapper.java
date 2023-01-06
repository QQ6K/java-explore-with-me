package ru.practicum.mappers;

import ru.practicum.models.ParticipationRequest;
import ru.practicum.models.ParticipationRequestDto;

import java.time.format.DateTimeFormatter;

public class ParticipationRequestMapper {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ParticipationRequestDto toParticipationRequestDto(ParticipationRequest participationRequest) {
        return new ParticipationRequestDto(participationRequest.getCreated().toString(),
                participationRequest.getEvent().getId(),
                participationRequest.getId(),
                participationRequest.getRequester().getId(),
                participationRequest.getState()
        );
    }
}
