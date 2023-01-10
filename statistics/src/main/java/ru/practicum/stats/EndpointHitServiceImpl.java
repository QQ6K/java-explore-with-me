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
    private final EndpointHitRepository endpointHitRepository;
    @Value("${app.name}")
    private String appName;

    @Override
    public EndpointHitDto create(EndpointHitDto endpointHitDto) {
        EndpointHitDto dto = StatMapper.toDto(endpointHitRepository.save(StatMapper.fromDto(endpointHitDto)));
        log.info("Сохранение endpointHitDto: id = {}, ip = {}, URI = {}, time = {}, app = {}",
                dto.getId(), dto.getIp(), dto.getTimestamp(), dto.getTimestamp(), dto.getApp());
        return dto;
    }

    @Override
    public Collection<ViewStatsDto> findHits(Parameters parameters) {
        if (parameters.getUnique()) {
            return parameters.getUris().stream().map(uri ->
                    new ViewStatsDto(appName, uri, endpointHitRepository
                            .getHitCountUnique(parameters.getStart(), parameters.getEnd(), uri)))
                    .collect(Collectors.toList());
        } else {
            System.out.println(endpointHitRepository.findAll());
            return parameters.getUris().stream().map(uri ->
                    new ViewStatsDto(appName, uri, endpointHitRepository
                            .getHitCountAll(parameters.getStart(), parameters.getEnd(), uri)))
                    .collect(Collectors.toList());
        }
    }
}
