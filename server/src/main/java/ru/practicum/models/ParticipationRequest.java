package ru.practicum.models;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(name = "requests")
public class ParticipationRequest {
        private LocalDateTime created;
        @ManyToOne
        private Event event;
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @ManyToOne
        private User requester;
        private String state;

}
