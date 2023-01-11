package ru.practicum.privatepart.events.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.enums.State;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.exceptions.CrudException;
import ru.practicum.exceptions.WrongObjectException;
import ru.practicum.mappers.EventMapper;
import ru.practicum.mappers.ParticipationRequestMapper;
import ru.practicum.models.*;
import ru.practicum.privatepart.events.interfaces.PrivateEventService;
import ru.practicum.privatepart.location.interfaces.PrivateLocationService;
import ru.practicum.publicpart.categories.interfaces.PublicCategoryService;
import ru.practicum.repositories.EventRepository;
import ru.practicum.repositories.LocationRepository;
import ru.practicum.repositories.ParticipationRequestRepository;
import ru.practicum.repositories.UserAdminRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PrivateEventServiceImpl implements PrivateEventService {

    private final UserAdminRepository userAdminRepository;

    private final EventRepository eventRepository;

    private final ParticipationRequestRepository participationRequestRepository;

    private final PublicCategoryService publicCategoryService;

    private final PrivateLocationService privateLocationService;

    private final LocationRepository locationRepository;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Event findById(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new WrongObjectException("События не существует id = " + eventId));
        log.debug("Поиск события id = {}", eventId);
        return event;
    }

    @Override
    public ParticipationRequest findParticipationRequestById(Long id) {
        log.debug("Поиск participation id={}", id);
        return participationRequestRepository.findById(id)
                .orElseThrow(() -> new WrongObjectException("Заявка не найдена id =" + id));
    }

    @Override
    public List<EventShortDto> findEventsByUserId(Long userId, Pageable pageable) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        List<Event> events = eventRepository.findByInitiator_IdOrderByEventDateDesc(userId, pageable);
        log.debug("Поиск событий, найдено {}", events.size());
        return events.stream().map(EventMapper::toShortDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EventFullDto updateEventByUser(UpdateEventRequest updateEventRequest, Long userId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Event event = eventRepository.getById(updateEventRequest.getEventId());
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new WrongObjectException("Событие другого пользователя");
        }
        if (!event.getState().equals("PENDING")) {
            throw new BadRequestException("Неверный статус события id = " + event.getId());
        }
        Optional.ofNullable(updateEventRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(updateEventRequest.getCategory())
                .ifPresent(eventFind -> event.setCategory(publicCategoryService.getCategoryById(eventFind)));
        Optional.of(LocalDateTime.parse(updateEventRequest.getEventDate(), formatter)).ifPresent(event::setEventDate);
        Optional.ofNullable(updateEventRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(updateEventRequest.getDescription()).ifPresent(event::setDescription);
        Optional.ofNullable(updateEventRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(updateEventRequest.getTitle()).ifPresent(event::setTitle);

        return EventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto createEvent(NewEventDto newEventDto, Long userId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Category category = publicCategoryService.getCategoryById(newEventDto.getCategory());
        Location location = locationRepository.findByLatAndLon(newEventDto.getLocation().getLat(), newEventDto.getLocation().getLon());
        if (location == null) {
            privateLocationService.createLocation(newEventDto.getLocation());
        }
        Event event = EventMapper.toEvent(newEventDto, category, user);
        event = eventRepository.save(event);
        log.debug("Создано событие id={} ", event.getId());
        return EventMapper.toFullDto(event);
    }

    @Override
    public EventFullDto findEventByInitiatorById(Long eventId, Long userId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Event event = this.findById(eventId);
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new CrudException("Пользователь не автор события");
        }
        log.debug("Найдено событие id = {}, пользователя id = {}", eventId, userId);
        return EventMapper.toFullDto(event);
    }

    @Override
    public EventFullDto findEventById(HttpServletRequest request, Long eventId) {
        Event event = this.findById(eventId);
        if (event.getState().equals("PUBLISHED")) {
            throw new WrongObjectException("Событие id = " + eventId + " не опубликовано");
        }
        log.debug("Поиск события id = {}", eventId);
        return EventMapper.toFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto cancelEventById(Long eventId, Long userId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Event event = this.findById(eventId);
        if (!user.getId().equals(event.getInitiator().getId())) {
            throw new CrudException("Пользователь не автор события");
        }
        event.setState("CANCELED");
        log.debug("Событие id={} отменено", event.getId());
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public ParticipationRequestDto confirmParticipationRequest(Long userId, Long eventId, Long reqId) {
        Event event = this.findById(eventId);
        ParticipationRequest participationRequest = this.findParticipationRequestById(reqId);
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new CrudException("Запрет изменения статуса: модерация");
        }
        if (!participationRequest.getState().equals("PENDING")) {
            throw new CrudException("Неподходящий статус заявки");
        }
        if (!participationRequest.getEvent().getId().equals(event.getId())) {
            throw new BadRequestException("Несовпадение id событий");
        }
        if (event.getParticipantLimit() == event.getParticipants().size()) {
            throw new CrudException("Достигнут лимит участников");
        } else if (event.getParticipantLimit() - event.getParticipants().size() == 1) {
            participationRequestRepository.saveAll(participationRequestRepository
                    .findByEventIdAndState(eventId, State.PENDING).stream()
                    .peek(e -> e.setState("CANCELED"))
                    .collect(Collectors.toList()));
        }
        participationRequest.setState("CONFIRMED");
        log.debug("Подтверждено участие id={}", participationRequest.getId());
        return ParticipationRequestMapper
                .toParticipationRequestDto(participationRequestRepository.save(participationRequest));
    }

    @Override
    @Transactional
    public ParticipationRequestDto rejectParticipationRequest(Long userId, Long eventId, Long reqId) {
        ParticipationRequest participationRequest = this.findParticipationRequestById(reqId);
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new WrongObjectException("События не существует id = " + eventId));

        if (!participationRequest.getEvent().getId().equals(event.getId())) {
            throw new BadRequestException("Несовпадение id событий");
        }
        if (!participationRequest.getState().equals("PENDING")) {
            throw new CrudException("Неподходящий статус заявки");
        }
        if (event.getParticipantLimit() == 0 || !event.getRequestModeration()) {
            throw new CrudException("Запрет изменения статуса: модерация");
        }
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        if (!event.getInitiator().getId().equals(user.getId())) {
            throw new WrongObjectException("Событие другого пользователя");
        }
        participationRequest.setState("REJECTED");
        log.debug("Заявка на участие отклонена id={}", participationRequest.getId());

        return ParticipationRequestMapper
                .toParticipationRequestDto(participationRequestRepository.save(participationRequest));
    }

    @Override
    public List<ParticipationRequestDto> findRequestByUserIdAndEventId(Long userId, Long eventId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Event event = this.findById(eventId);
        if (user.equals(event.getInitiator())) {
            log.debug("Посик заявок на участие в событии id = {} пользователя id = {}", eventId, userId);
            return participationRequestRepository.findByEventInitiatorIdAndEventId(userId, eventId).stream()
                    .map(ParticipationRequestMapper::toParticipationRequestDto).collect(Collectors.toList());
        } else {
            throw new WrongObjectException("Пользователь не инициатор события!");
        }
    }

}