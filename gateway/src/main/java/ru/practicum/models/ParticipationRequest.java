package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.State;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ParticipationRequest {
        private LocalDateTime created;
        private Event event;
        private Long id;
        private User requester;
        private State state;

}
