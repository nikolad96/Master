package com.example.bitcoinservice.controller;

import org.springframework.beans.factory.annotation.Autowired;

public class Nesto implements Runnable{
    @Autowired
    Monitor m;

    private Integer id;
    private String secret;
    private Integer rad_id;
    private Integer casopis_id;
    private Integer korisnik_id;

        public Nesto(Integer id, String secret, Integer rad_id, Integer casopis_id, Integer korisnik_id){
        this.id = id;
        this.secret = secret;
        this.rad_id = rad_id;
        this.casopis_id = casopis_id;
        this.korisnik_id = korisnik_id;

    }

    public void run(){
        m.mon(this.id, this.secret, this.rad_id, this.casopis_id, this.korisnik_id);
    }
}
