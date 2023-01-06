package ru.practicum.publicpart.events.interfaces;

import org.springframework.data.domain.Pageable;
import ru.practicum.models.EventFullDto;
import ru.practicum.models.EventShortDto;

import java.util.Collection;

public interface PublicEventsService {

    EventFullDto findById(Long eventId, String requestURI, String remoteAddr);

    Collection<EventShortDto> findEvents(String text,
                                         Long[] catIds,
                                         Boolean paid,
                                         String rangeStart,
                                         String rangeEnd,
                                         Boolean onlyAvailable,
                                         Pageable pageable,
                                         String clientIp,
                                         String endpointPath);

}
