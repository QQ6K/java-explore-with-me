package ru.practicum.privatepart.subscriptions;

import ru.practicum.models.Subscription;

public class SubscriptionMapper {

    public static Long toLong(Subscription subscription) {
        return subscription.getSubscription().getSubscriptionId();
    }

}
