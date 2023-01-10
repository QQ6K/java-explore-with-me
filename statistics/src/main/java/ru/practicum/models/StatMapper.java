package ru.practicum.models;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class StatMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static EndpointHitDto toDto(EndpointHit endpointHit) {
        return new EndpointHitDto(endpointHit.getId(), endpointHit.getApp(), endpointHit.getUri(), endpointHit.getIp(),
                endpointHit.getTimestamp().format(formatter));
    }

    public static EndpointHit fromDto(EndpointHitDto endpointHitDto) {
        return new EndpointHit(endpointHitDto.getId(), endpointHitDto.getApp(), endpointHitDto.getUri(), endpointHitDto.getIp(),
                LocalDateTime.parse(endpointHitDto.getTimestamp(), formatter));
    }

    public static Parameters fromParametersDto(ParametersDto parametersDto) {
        String decStart = java.net.URLDecoder.decode(parametersDto.getStart(), StandardCharsets.UTF_8);
        String decEnd = java.net.URLDecoder.decode(parametersDto.getStart(), StandardCharsets.UTF_8);
        LocalDateTime start = LocalDateTime.parse(decStart, formatter);
        LocalDateTime end = LocalDateTime.parse(decEnd, formatter);
        List<String> uris = Arrays.asList(parametersDto.getUris());
        Boolean unique = parametersDto.getUnique();
        return new Parameters(start, end, uris, unique);
    }

    public static ViewStatsDto fromViewStatsToDto(ViewStats viewStats) {
        return new ViewStatsDto(viewStats.getUri(), viewStats.getUri(), viewStats.getHits());
    }

}
