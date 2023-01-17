package ru.practicum.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "endpointhit")
@Getter
@Setter
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
    private Long timestamp;
}
