package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.practicum.models.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EndpointHitServiceImpl implements EndpointHitService{
    private final EndpointHitRepository repository;
    @Value("${app.name}")
    private String appName;
    /*@Value("http://localhost:7070")
    private String urlMainService;*/

    @Override
    public EndpointHitDto save(EndpointHitDto endpointHitDto) {
        return EndpointHitMapper.toDto(repository.save(EndpointHitMapper.fromDto(endpointHitDto)));
    }

    @Override
    public Collection<ViewStatsDto> getStat(ParametersDto parametersDto) {
        Parameters parameters = EndpointHitMapper.fromParametersDto(parametersDto);
        if (parameters.getUnique()) {
            return parameters.getUris().stream().map(uri -> new ViewStatsDto(appName, uri, repository.getHitCountUnique(parameters.getStart(), parameters.getEnd(), uri))).collect(Collectors.toList());
        } else {
            return parameters.getUris().stream().map(uri -> new ViewStatsDto(appName, uri, repository.getHitCountAll(parameters.getStart(), parameters.getEnd(), uri))).collect(Collectors.toList());
        }
    }
}
