package ru.practicum.stats;

import ru.practicum.models.*;

import java.util.Collection;

public interface EndpointHitService {
    EndpointHitDto create(EndpointHitDto endpointHitDto);

    Collection<ViewStatsDto> findHits(Parameters parameters);

}
