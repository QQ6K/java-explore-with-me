package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "endpointhit")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EndpointHit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String app;
    @NonNull
    private String uri;
    @NonNull
    private String ip;
    @NonNull
    private LocalDateTime timestamp;
}
