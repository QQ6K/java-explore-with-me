package ru.practicum.publicpart.events.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.client.Client;
import ru.practicum.exceptions.CrudException;
import ru.practicum.mappers.EventMapper;
import ru.practicum.models.EndpointHitDto;
import ru.practicum.models.EventFullDto;
import ru.practicum.models.EventShortDto;
import ru.practicum.publicpart.events.interfaces.PublicEventsService;
import ru.practicum.repositories.EventRepository;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Service
@RequiredArgsConstructor
@Slf4j
public class PublicEventsServiceImpl implements PublicEventsService {
    private final Client client;
    private final EventRepository eventRepository;
    @Value("${app.name}")
    private String appName;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public EventFullDto findById(Long eventId, String requestURI, String remoteAddr) {
        EndpointHitDto endpointHitDto = new EndpointHitDto(null, appName, requestURI, remoteAddr,
                formatter.format(LocalDateTime.now()));
        client.post(endpointHitDto);
        log.debug("найти событие id={}", eventId);
        return EventMapper.toFullDto(eventRepository.findById(eventId)
                .orElseThrow(() -> new CrudException("id = " + eventId)));
    }

    @Override
    public Collection<EventShortDto> findEvents(String text,
                                                Long[] catIds,
                                                Boolean paid,
                                                String rangeStartString,
                                                String rangeEndString,
                                                Boolean onlyAvailable,
                                                Pageable pageable,
                                                String clientIp,
                                                String endpointPath) {
        LocalDateTime rangeStart = null;
        LocalDateTime rangeEnd = null;
        if (rangeStartString != null && rangeEndString != null) {
            rangeStart = LocalDateTime.parse(URLDecoder.decode(rangeStartString, StandardCharsets.UTF_8), formatter);
            rangeEnd = LocalDateTime.parse(URLDecoder.decode(rangeEndString, StandardCharsets.UTF_8), formatter);
        }
        if (onlyAvailable) {
            EndpointHitDto endpointHitDto = new EndpointHitDto(null, appName, endpointPath, clientIp,
                    formatter.format(LocalDateTime.now()));
            try {
                client.post(endpointHitDto);
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }
            log.debug("Поиск событий");
            return eventRepository.findAllForPublicAvailable(text, catIds, paid,
                            rangeStart, rangeEnd, pageable).map(EventMapper::toShortDto)
                    .toList();
        } else {
            EndpointHitDto endpointHitDto = new EndpointHitDto(null, appName, endpointPath, clientIp,
                    formatter.format(LocalDateTime.now()));
            try {
                client.post(endpointHitDto);
            } catch (RuntimeException e) {
                log.error(e.getMessage());
            }
            return eventRepository.findAllForPublic(URLDecoder.decode(text,StandardCharsets.UTF_8), catIds, paid,
                            rangeStart, rangeEnd, pageable).map(EventMapper::toShortDto)
                    .toList();
        }
    }
}
