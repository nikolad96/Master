package com.example.naucnacentrala.dto;

import com.example.naucnacentrala.model.NaucnaOblast;

import java.util.ArrayList;
import java.util.List;

public class RadDTO {

    private Integer id;
    private String naslov;
    private String apstrakt;
    private String pdfLokacija;
//    private NaucnaOblast naucnaOblast;
    private boolean prihvacen;
    private boolean kupljen;
    private List<String> kljucniPojmovi = new ArrayList<>();

    public RadDTO() {
    }

    public RadDTO(Integer id, String naslov, String apstrakt, String pdfLokacija, boolean prihvacen, List<String> kljucniPojmovi) {
        this.id = id;
        this.naslov = naslov;
        this.apstrakt = apstrakt;
        this.pdfLokacija = pdfLokacija;
        this.prihvacen = prihvacen;
        this.kljucniPojmovi = kljucniPojmovi;
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

    public String getPdfLokacija() {
        return pdfLokacija;
    }

    public void setPdfLokacija(String pdfLokacija) {
        this.pdfLokacija = pdfLokacija;
    }

//    public NaucnaOblast getNaucnaOblast() {
//        return naucnaOblast;
//    }
//
//    public void setNaucnaOblast(NaucnaOblast naucnaOblast) {
//        this.naucnaOblast = naucnaOblast;
//    }

    public boolean isPrihvacen() {
        return prihvacen;
    }

    public void setPrihvacen(boolean prihvacen) {
        this.prihvacen = prihvacen;
    }

    public boolean isKupljen() { return kupljen; }

    public void setKupljen(boolean kupljen) { this.kupljen = kupljen; }

    public List<String> getKljucniPojmovi() {
        return kljucniPojmovi;
    }

    public void setKljucniPojmovi(List<String> kljucniPojmovi) {
        this.kljucniPojmovi = kljucniPojmovi;
    }
}
