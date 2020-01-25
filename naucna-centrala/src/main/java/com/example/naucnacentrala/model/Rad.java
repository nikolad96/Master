package com.example.naucnacentrala.model;

import com.example.naucnacentrala.utils.StringListConverter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String naslov;

    @Column
    private String apstrakt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private NaucnaOblast naucnaOblast;

    @Column
    private String pdfLokacija;

    @Column
    private boolean prihvacen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Korisnik> koautori = new ArrayList<>();

    @Convert(converter = StringListConverter.class)
    private List<String> kljucniPojmovi = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Casopis casopis;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Korisnik> korisniciPlatili = new ArrayList<>();

    public Rad() {
    }

    public Rad(String naslov, String apstrakt, NaucnaOblast naucnaOblast, String pdfLokacija, boolean prihvacen) {
        this.naslov = naslov;
        this.apstrakt = apstrakt;
        this.naucnaOblast = naucnaOblast;
        this.pdfLokacija = pdfLokacija;
        this.prihvacen = prihvacen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getApstrakt() {
        return apstrakt;
    }

    public void setApstrakt(String apstrakt) {
        this.apstrakt = apstrakt;
    }

    public NaucnaOblast getNaucnaOblast() {
        return naucnaOblast;
    }

    public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
        this.naucnaOblast = naucnaOblast;
    }

    public String getPdfLokacija() {
        return pdfLokacija;
    }

    public void setPdfLokacija(String pdfLokacija) {
        this.pdfLokacija = pdfLokacija;
    }

    public boolean isPrihvacen() {
        return prihvacen;
    }

    public void setPrihvacen(boolean prihvacen) {
        this.prihvacen = prihvacen;
    }

    public List<Korisnik> getKoautori() { return koautori; }

    public void setKoautori(List<Korisnik> koautori) {
        this.koautori = koautori;
    }

    public List<String> getKljucniPojmovi() {
        return kljucniPojmovi;
    }

    public void setKljucniPojmovi(List<String> kljucniPojmovi) {
        this.kljucniPojmovi = kljucniPojmovi;
    }

    public Casopis getCasopis() { return casopis; }

    public void setCasopis(Casopis casopis) { this.casopis = casopis; }

    public List<Korisnik> getKorisniciPlatili() { return korisniciPlatili; }

    public void setKorisniciPlatili(List<Korisnik> korisniciPlatili) { this.korisniciPlatili = korisniciPlatili; }
}
