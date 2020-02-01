package com.example.naucnacentrala.repository;

import com.example.naucnacentrala.model.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepositorium extends JpaRepository<Subscription, Integer> {

    Subscription findOneById(Integer id);
}
