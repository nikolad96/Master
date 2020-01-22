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
    public Account findOneByPan(String pan){ return accountRepository.findOneByPan(pan); }
    public List<Account> findAll(){
        return accountRepository.findAll();
    }
    public Account save(Account account){
        return accountRepository.save(account);
    }
    public List<Account> findAccount (String pan, String securityCode, String cardholderName, String expirationDate) { return accountRepository.findAccount(pan, securityCode, cardholderName, expirationDate); }

    public boolean isCardExpired(Account account){
        String date = account.getExpirationDate();
        Date today = new Date();

        int yearToday = today.getYear();
        int monthToday = today.getMonth();
        System.out.println("year: " + yearToday + "; month: " + monthToday);

        String yearString = date.substring(2);
        String monthString = date.substring(0, 2);
        System.out.println("yearString: " + yearString + "; monthString: " + monthString);
        int year = Integer.parseInt(yearString);
        int month = Integer.parseInt(monthString);

        if(yearToday > year){
            return false;
        }else{
            if(monthToday > month){
                return false;
            }else{
                return true;
            }
        }

    }

}
