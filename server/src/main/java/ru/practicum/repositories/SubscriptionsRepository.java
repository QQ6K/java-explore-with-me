package ru.practicum.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.models.Subscription;

import java.util.Collection;

@Repository
public interface SubscriptionsRepository extends JpaRepository<Subscription, Long> {

    @Query(value = "SELECT s FROM Subscription s WHERE s.subscription.userId = :id")
    Collection<Subscription> findByUser_id(Long id);

    @Query(value = "SELECT s FROM Subscription s WHERE s.subscription.userId = :id AND s.subscription.subscriptionId = :subscriptionId")
    Subscription findByUserIdAndSubId(Long id, Long subscriptionId);
}
