package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class SubscriptionId implements Serializable {
    private Long userId;
    private Long subscriptionId;
}
