package com.example.banka.repository;

import com.example.banka.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findOneById(Integer id);

    //@Modifying
    @Query("select account from Account account where account.pan = ?1 and account.securityCode = ?2 and account.cardholderName = ?3 and account.expirationDate = ?4")
    List<Account> findAccount (String pan, String securityCode, String cardholderName, String expirationDate);
}
