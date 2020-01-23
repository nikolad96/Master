package com.example.banka.service;

import com.example.banka.model.Account;
import com.example.banka.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String todayString = dateFormat.format(today);

//        System.out.println("todayString: " + todayString);
        int yearToday = Integer.parseInt(todayString.substring(0,4));
        int monthToday = Integer.parseInt(todayString.substring(5, 7));
//        System.out.println("yearToday: " + yearToday + "; monthToday: " + monthToday);

        int year = Integer.parseInt(date.substring(3));
        int month = Integer.parseInt(date.substring(0, 2));
//        System.out.println("year: " + year + "; month: " + month);

        if(yearToday > year) {
            return true;
        }
        if(yearToday < year){
            return false;
        }
        if(yearToday == year && monthToday > month){
            return true;
        }else{
            return false;
        }

    }

}
