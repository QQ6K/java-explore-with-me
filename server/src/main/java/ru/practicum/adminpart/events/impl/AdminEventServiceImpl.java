package ru.practicum.adminpart.events.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.adminpart.events.interfaces.AdminEventService;
import ru.practicum.exceptions.CrudException;
import ru.practicum.mappers.EventMapper;
import ru.practicum.models.AdminUpdateEventRequest;
import ru.practicum.models.Event;
import ru.practicum.models.EventFullDto;
import ru.practicum.publicpart.categories.interfaces.PublicCategoryService;
import ru.practicum.repositories.EventRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.practicum.enums.State.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminEventServiceImpl implements AdminEventService {
    private final EventRepository eventRepository;
    private final PublicCategoryService publicCategoryService;
    //private final LocationService locationService;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    @Transactional
    public EventFullDto updateEventRequest(Long eventId, AdminUpdateEventRequest adminUpdateEventRequest) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CrudException("Не найдено событие id = " + eventId));
        Optional.ofNullable(adminUpdateEventRequest.getAnnotation()).ifPresent(event::setAnnotation);
        Optional.ofNullable(adminUpdateEventRequest.getCategory())
                .ifPresent(eventFind -> event.setCategory(publicCategoryService.getCategoryById(eventFind)));
        Optional.ofNullable(adminUpdateEventRequest.getDescription()).ifPresent(event::setDescription);
        Optional.of(LocalDateTime.parse(adminUpdateEventRequest.getEventDate(), formatter)).ifPresent(event::setEventDate);
        Optional.ofNullable(adminUpdateEventRequest.getLocation()).ifPresent(event::setLocation);
        Optional.ofNullable(adminUpdateEventRequest.getPaid()).ifPresent(event::setPaid);
        Optional.ofNullable(adminUpdateEventRequest.getParticipantLimit()).ifPresent(event::setParticipantLimit);
        Optional.ofNullable(adminUpdateEventRequest.getRequestModeration()).ifPresent(event::setRequestModeration);
        Optional.ofNullable(adminUpdateEventRequest.getTitle()).ifPresent(event::setTitle);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto publishEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CrudException("Не найдено событие id = " + eventId));
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new CrudException("Публикация раньше 2 часов" +
                    event.getEventDate());
        }
        if (!event.getState().equals(PENDING)) {
            throw new CrudException("Статус отличается от WAITING");
        }
        event.setState(PUBLISHED);
        event.setPublishedOn(LocalDateTime.now());
        log.info("Публикация события {}, id = {}", formatter.format(LocalDateTime.now()), event.getId());
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    @Transactional
    public EventFullDto rejectEvent(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new CrudException("Не найдено событие id = " + eventId));
        if (event.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new CrudException("Публикация раньше 2 часов" +
                    event.getEventDate());
        }
        if (!event.getState().equals(PENDING)) {
            throw new CrudException("Статус отличается от WAITING");
        }
        event.setState(CANCELED);
        log.debug("Отклонение события id = {}", eventId);
        return EventMapper.toFullDto(eventRepository.save(event));
    }

    @Override
    public List<EventFullDto> findAllEvents(Long[] users, String[] states, Long[] categories,
                                            LocalDateTime rangeStart, LocalDateTime rangeEnd, Pageable pageable) {
        if (rangeStart.isAfter(rangeEnd)) {
            throw new CrudException("Старт после завершения");
        }
        log.debug("Поиск всех событий");
        List<Event> events = eventRepository.findAllEventsAdmin(users, categories,
                        states, rangeStart, rangeEnd, pageable)
                .toList();
        return events.stream().map(EventMapper::toFullDto).collect(Collectors.toList());
    }

    @Override
    public List<Event> findEventsByIds(List<Long> ids) {
        log.debug("Поиск событий");
        return eventRepository.findByIdIn(ids);
    }

    @Override
    public Event findEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new CrudException("Не найдено событие id = " + eventId));
    }
}
