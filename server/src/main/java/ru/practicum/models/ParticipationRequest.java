package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.enums.State;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data
@AllArgsConstructor
@Table(name = "requests")
public class ParticipationRequest {
        private LocalDateTime created;
        @ManyToOne
        @JoinColumn(name = "events", referencedColumnName = "id")
        private Event event;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        @JoinColumn(name = "user", referencedColumnName = "id")
        private User requester;
        private State state;

}
