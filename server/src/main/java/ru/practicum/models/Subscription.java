package ru.practicum.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subscriptions")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class Subscription {
    @EmbeddedId
    private SubscriptionId subscription;
}



