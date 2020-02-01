package com.example.paypalservice.repositorium;

import com.example.paypalservice.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepositorium extends JpaRepository<Subscription, Integer> {

    Subscription findOneById(Integer id);
}
