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
public class EndpointHitServiceImpl implements EndpointHitService {
    private final EndpointHitRepository repository;
    @Value("${app.name}")
    private String appName;

    @Override
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHitDto dto = StatMapper.toDto(repository.save(StatMapper.fromDto(endpointHitDto)));
        log.info("Сохранение endpointHitDto: id = {}, ip = {}, URI = {}, time = {}, app = {}",
                dto.getId(), dto.getIp(), dto.getTimestamp(), dto.getTimestamp(), dto.getApp());
        return dto;
    }

    @Override
    public Collection<ViewStatsDto> getStat(ParametersDto parametersDto) {
        Parameters parameters = StatMapper.fromParametersDto(parametersDto);
        log.info("Получение статистики");
        if (parameters.getUnique()) {
            return parameters.getUris().stream().map(uri -> StatMapper
                            .fromViewStatsToDto(new ViewStats(appName, uri, repository
                                    .getHitCountUnique(parameters.getStart(), parameters.getEnd(), uri))))
                    .collect(Collectors.toList());
        } else {
            return parameters.getUris().stream().map(uri -> StatMapper
                            .fromViewStatsToDto(new ViewStats(appName, uri, repository
                                    .getHitCountAll(parameters.getStart(), parameters.getEnd(), uri))))
                    .collect(Collectors.toList());
        }
    }
}
