package ru.practicum.stats;

import ru.practicum.models.EndpointHitDto;
import ru.practicum.models.ParametersDto;
import ru.practicum.models.ViewStatsDto;

import java.util.Collection;

public interface EndpointHitService {
    EndpointHitDto create(EndpointHitDto endpointHitDto);

    Collection<ViewStatsDto> getStat(ParametersDto parametersDto);
}
