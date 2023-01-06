package ru.practicum.models;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class EndpointHitMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EndpointHitDto toDto(EndpointHit endpointHit) {
        return new EndpointHitDto(endpointHit.getId(), endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(),
                formatter.format(LocalDateTime.ofEpochSecond(endpointHit.getTimestamp(), 0, ZoneOffset.UTC)));
    }

    public static EndpointHit fromDto(EndpointHitDto dto) {
        return new EndpointHit(dto.getId(), dto.getApp(), dto.getUri(), dto.getIp(),
                LocalDateTime.parse(dto.getTimestamp(), formatter).toEpochSecond(ZoneOffset.UTC));
    }

    public static Parameters fromParametersDto(ParametersDto parametersDto) {
        LocalDateTime start = LocalDateTime.parse(parametersDto.getStart(), formatter);
        LocalDateTime end = LocalDateTime.parse(parametersDto.getEnd(), formatter);
        List<String> uris = Arrays.asList(parametersDto.getUris());
        Boolean unique = parametersDto.getUnique();
        return new Parameters(start, end, uris, unique);
    }

}
