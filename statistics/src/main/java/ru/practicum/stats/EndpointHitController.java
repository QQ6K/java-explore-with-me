package ru.practicum.stats;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.models.EndpointHitDto;
import ru.practicum.models.ParametersDto;
import ru.practicum.models.ViewStatsDto;

import java.util.Collection;

@RestController
@Slf4j
@RequiredArgsConstructor
public class EndpointHitController {

    private final EndpointHitService endpointHitService;

    @PostMapping("/hit")
    public EndpointHitDto createHit(@RequestBody EndpointHitDto endpointHitDto) {
        endpointHitService.save(endpointHitDto);
        return endpointHitDto;
    }

    @GetMapping("/stats")
    public Collection<ViewStatsDto> getStat(@RequestParam("start") String start,
                                            @RequestParam("end") String end,
                                            @RequestParam("uris") String[] uris,
                                            @RequestParam("unique") Boolean unique) {
        ParametersDto parametersDto = new ParametersDto(start, end, uris, unique);
        return endpointHitService.getStat(parametersDto);
    }
}
