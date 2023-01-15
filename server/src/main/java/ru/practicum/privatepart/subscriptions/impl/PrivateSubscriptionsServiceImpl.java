package ru.practicum.privatepart.subscriptions.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exceptions.BadRequestException;
import ru.practicum.exceptions.WrongObjectException;
import ru.practicum.mappers.EventMapper;
import ru.practicum.mappers.UserMapper;
import ru.practicum.models.*;
import ru.practicum.privatepart.subscriptions.SubscriptionMapper;
import ru.practicum.privatepart.subscriptions.interfaces.PrivateSubscriptionService;
import ru.practicum.repositories.EventRepository;
import ru.practicum.repositories.SubscriptionsRepository;
import ru.practicum.repositories.UserAdminRepository;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PrivateSubscriptionsServiceImpl implements PrivateSubscriptionService {
    private final EventRepository eventRepository;

    private final SubscriptionsRepository subscriptionsRepository;

    private final UserAdminRepository userAdminRepository;

    @Override
    public List<EventShortDto> getFeed(SearchParameters searchParameters) {
        userAdminRepository.findById(searchParameters.getUserId()).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + searchParameters.getUserId()));
        Collection<Subscription> subscriptions = getUsersSubscriptions(searchParameters.getUserId());
        Long[] tmp = new Long[0];
        Long[] feedSubs = subscriptions.stream().map(SubscriptionMapper::toLong).collect(Collectors.toList()).toArray(tmp);
        log.debug("Лента пользователя id = {}", searchParameters.getUserId());
        List<EventShortDto> eventsFeed = Collections.emptyList();
        if (null != searchParameters.getOnlyAvailable()) {
            if (searchParameters.getOnlyAvailable()) {
                eventsFeed = eventRepository
                        .findAllForPublicAvailableUserSubscription(
                                feedSubs, searchParameters.getCatId(), searchParameters.getPaid(),
                                searchParameters.getRangeStart(), searchParameters.getRangeEnd(), searchParameters.getPageable())
                        .stream().map(EventMapper::toShortDto).collect(Collectors.toList());
            } else {
                eventsFeed = eventRepository
                        .findAllForPublicUserSubscription(
                                feedSubs, searchParameters.getCatId(), searchParameters.getPaid(),
                                searchParameters.getRangeStart(), searchParameters.getRangeEnd(), searchParameters.getPageable())
                        .stream().map(EventMapper::toShortDto).collect(Collectors.toList());
            }
        } else eventsFeed = eventRepository
                .findAllForPublicUserSubscription(
                        feedSubs, searchParameters.getCatId(), searchParameters.getPaid(),
                        searchParameters.getRangeStart(), searchParameters.getRangeEnd(), searchParameters.getPageable())
                .stream().map(EventMapper::toShortDto).collect(Collectors.toList());
        return eventsFeed;
    }

    @Transactional
    @Override
    public Subscription addUsersSubscription(Long userId, Long subscriptionId) {
        if (Objects.equals(userId, subscriptionId)) {
            throw new WrongObjectException("Зачем подписываться на себя?");
        }
        User user = userAdminRepository.findById(subscriptionId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + subscriptionId));
        if (user.getLock()) {
            throw new BadRequestException("Запрет подписки userId = " + subscriptionId);
        }
        userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Subscription subscription = new Subscription(new SubscriptionId(userId, subscriptionId));
        if (null != subscriptionsRepository.findByUserIdAndSubId(userId,subscriptionId)) {
            throw new DataIntegrityViolationException("Подписка уже существует");
        }
        subscription = subscriptionsRepository.save(subscription);
        log.debug("Подписки пользователя id = {}", userId);
        return subscription;
    }

    @Transactional
    @Override
    public void deleteUsersSubscription(Long userId, Long subscriptionId) {
        userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        userAdminRepository.findById(subscriptionId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + subscriptionId));
        Subscription subscription = new Subscription(new SubscriptionId(userId, subscriptionId));
        try {
            subscriptionsRepository.findByUserIdAndSubId(userId, subscriptionId);
        } catch (RuntimeException e) {
            throw new WrongObjectException("Подписки на пользователя" + subscriptionId + " не существует");
        }
        log.debug("Отменить подписку пользователя id = {}", userId);
        subscriptionsRepository.delete(subscription);
    }

    @Override
    public Collection<Subscription> getUsersSubscriptions(Long userId) {
        log.debug("Получить подписки пользователя id = {}", userId);
        userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        Collection<Subscription> subscription = subscriptionsRepository.findByUser_id(userId);
        return subscription;
    }

    @Transactional
    @Override
    public UserDto lockUserSubscription(Long userId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        if (user.getLock()) {
            throw new BadRequestException("Блокировка пользователя уже включена userId = " + userId);
        }
        user.setLock(true);
        user = userAdminRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    @Transactional
    @Override
    public UserDto unlockUserSubscription(Long userId) {
        User user = userAdminRepository.findById(userId).orElseThrow(() ->
                new WrongObjectException("Пользователя не существует id = " + userId));
        if (!user.getLock()) {
            throw new BadRequestException("Блокировка пользователя уже включена userId = " + userId);
        }
        user.setLock(false);
        user = userAdminRepository.save(user);
        return UserMapper.toUserDto(user);
    }
}
