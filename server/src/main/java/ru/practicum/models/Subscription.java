package ru.practicum.models;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Subscription {
    @EmbeddedId
    private SubscriptionId subscription;
}



