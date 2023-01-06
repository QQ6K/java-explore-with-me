package ru.practicum.mappers;

import ru.practicum.models.Location;
import ru.practicum.models.LocationDto;

public class LocationMapper {
    public static LocationDto toDto(Location location) {
        return new LocationDto(location.getLat(), location.getLon());
    }
}
