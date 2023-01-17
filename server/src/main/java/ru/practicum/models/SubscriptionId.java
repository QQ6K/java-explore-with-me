package ru.practicum.models;

import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class SubscriptionId implements Serializable {
    private Long userId;
    private Long subscriptionId;
}
