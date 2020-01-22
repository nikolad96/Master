package com.example.paymentcardcenter.repository;

import com.example.paymentcardcenter.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findOneById(Integer id);

}
