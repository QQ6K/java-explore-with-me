package ru.practicum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.models.Location;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByLatAndLon(Float lat, Float lon);
}
