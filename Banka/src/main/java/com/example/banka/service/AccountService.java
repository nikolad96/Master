package com.example.banka.service;

import com.example.banka.model.Account;
import com.example.banka.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account findOneById(Integer id){ return accountRepository.findOneById(id); }
    public List<Account> findAll(){
        return accountRepository.findAll();
    }
    public Account save(Account account){
        return accountRepository.save(account);
    }
    public List<Account> findAccount (String pan, String securityCode, String cardholderName, String expirationDate) { return accountRepository.findAccount(pan, securityCode, cardholderName, expirationDate); }

}
