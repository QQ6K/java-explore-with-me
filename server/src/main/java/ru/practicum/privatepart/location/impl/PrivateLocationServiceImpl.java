package ru.practicum.privatepart.location.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.models.Location;
import ru.practicum.privatepart.location.interfaces.PrivateLocationService;
import ru.practicum.repositories.LocationRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PrivateLocationServiceImpl implements PrivateLocationService {
    private final LocationRepository locationRepository;

    @Override
    public Location findByLatAndLon(Float lat, Float lon) {
        log.debug("Поиск локации");
        return locationRepository.findByLatAndLon(lat, lon);
    }

    @Override
    public Location createLocation(Location location) {
        location = locationRepository.save(location);
        log.info("Новая локация id = " + location.getId());
        return location;
    }
}
