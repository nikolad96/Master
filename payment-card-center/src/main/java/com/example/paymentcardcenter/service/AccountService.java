package com.example.paymentcardcenter.service;

import com.example.paymentcardcenter.model.Account;
import com.example.paymentcardcenter.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
