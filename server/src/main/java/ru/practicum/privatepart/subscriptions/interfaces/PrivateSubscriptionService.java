package ru.practicum.privatepart.subscriptions.interfaces;

import org.springframework.transaction.annotation.Transactional;
import ru.practicum.models.EventShortDto;
import ru.practicum.models.SearchParameters;
import ru.practicum.models.Subscription;
import ru.practicum.models.UserDto;

import java.util.Collection;
import java.util.List;

public interface PrivateSubscriptionService {

    List<EventShortDto> getFeed(SearchParameters searchParameters);

    Subscription addUsersSubscription(Long userId, Long sbId);

    @Transactional
    void deleteUsersSubscription(Long userId, Long subscriptionId);

    Collection<Subscription> getUsersSubscriptions(Long userId);

    @Transactional
    UserDto lockUserSubscription(Long userId);

    @Transactional
    UserDto unlockUserSubscription(Long userId);
}
