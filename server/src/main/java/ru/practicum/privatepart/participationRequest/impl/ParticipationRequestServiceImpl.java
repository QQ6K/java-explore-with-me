package ru.practicum.privatepart.participationRequest.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.State;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.exceptions.CrudException;
import ru.practicum.exceptions.WrongObjectException;
import ru.practicum.mappers.ParticipationRequestMapper;
import ru.practicum.models.Event;
import ru.practicum.models.ParticipationRequest;
import ru.practicum.models.ParticipationRequestDto;
import ru.practicum.models.User;
import ru.practicum.privatepart.events.interfaces.PrivateEventService;
import ru.practicum.privatepart.participationRequest.interfaces.ParticipationRequestService;
import ru.practicum.repositories.ParticipationRequestRepository;
import ru.practicum.repositories.UserAdminRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.enums.State.CANCELED;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ParticipationRequestServiceImpl implements ParticipationRequestService {

    private final ParticipationRequestRepository participationRequestRepository;

    private final PrivateEventService privateEventService;

    private final UserAdminRepository userAdminRepository;

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public ParticipationRequest findByRequestId(Long requestId) {
        ParticipationRequest request = participationRequestRepository.findById(requestId).orElseThrow(() ->
                new WrongObjectException("Заявки не существует id = " + requestId));
        log.debug("Поиск запрос на участие id = {}", requestId);
        return request;
    }

    @Override
    public List<ParticipationRequestDto> findAllByParticipantId(Long userId) {
        List<ParticipationRequest> requests = participationRequestRepository.findByRequesterId(userId);
        log.debug("Поиск запросов на участие id = {}", userId);
        return requests.stream().map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ParticipationRequestDto createParticipation(Long userId, Optional<Long> eventId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Event event = privateEventService.findById(
                eventId.orElseThrow(() -> new BadRequestException("Отсутствует событие id = {}" + eventId)));
        if (event.getInitiator().getId().equals(userId)) {
            throw new CrudException("id = " + userId + " инициатора события и запрашивающего совпадают");
        }
        if (participationRequestRepository.findByEvent_IdAndRequesterId(event.getId(), userId) != null) {
            throw new BadRequestException("Запрос совпадает с ранее созданным");
        }
        if (!event.getState().equals(State.PUBLISHED)) {
            throw new BadRequestException("Событие не опубликовано");
        }
        if (event.getParticipantLimit() <= event.getParticipants().size()) {
            throw new CrudException("Превышен лимит участников :" + event.getParticipants().size());
        }
        ParticipationRequest participationRequest = new ParticipationRequest();
        participationRequest.setRequester(user);
        participationRequest.setState(State.PENDING);
        participationRequest.setEvent(event);
        participationRequest.setCreated(LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter));
        participationRequest = participationRequestRepository.save(participationRequest);
        log.debug("Создана заявка на участие id={}", participationRequest.getId());
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelRequestByUser(Long userId, Long requestId) {
        ParticipationRequest participationRequest = this.findByRequestId(requestId);
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Event event = participationRequest.getEvent();
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new CrudException("Запрет изменения статуса: модерация");
        }
        if (!participationRequest.getState().equals(State.PENDING)) {
            throw new CrudException("Неподходящий статус заявки");
        }
        if (!participationRequest.getRequester().getId().equals(user.getId())) {
            throw new CrudException("Событие другого пользователя");
        }
        participationRequest.setState(CANCELED);
        participationRequest = participationRequestRepository.save(participationRequest);
        return ParticipationRequestMapper.toParticipationRequestDto(participationRequest);
    }

}
