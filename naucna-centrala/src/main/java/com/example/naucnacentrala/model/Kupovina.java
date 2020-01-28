package com.example.naucnacentrala.model;

import javax.persistence.*;

@Entity
public class Kupovina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer radId;

    @Column
    private Integer casopisId;

    @Column
    private Integer paymentId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Korisnik korisnik;

    public Kupovina() {
    }

    public Kupovina(Integer radId, Integer paymentId, Korisnik korisnik) {
        this.radId = radId;
        this.paymentId = paymentId;
        this.korisnik = korisnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRadId() {
        return radId;
    }

    public void setRadId(Integer radId) {
        this.radId = radId;
    }

    public Integer getCasopisId() {
        return casopisId;
    }

    public void setCasopisId(Integer casopisId) {
        this.casopisId = casopisId;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }
}
