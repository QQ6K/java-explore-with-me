package ru.practicum.privatepart.location.interfaces;

import ru.practicum.models.Location;

public interface PrivateLocationService {
    Location findByLatAndLon(Float lat, Float lon);

    Location createLocation(Location location);
}
