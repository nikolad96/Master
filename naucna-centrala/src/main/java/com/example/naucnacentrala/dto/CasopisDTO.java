package com.example.naucnacentrala.dto;

import com.example.naucnacentrala.model.NaplacujeClanarina;
import com.example.naucnacentrala.model.Rad;

import java.util.List;

public class CasopisDTO {
    private Integer id;
    private String name;
    private String issn;
    private NaplacujeClanarina naplacujeClanarina;

    List<Rad> radovi;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIssn() {
        return issn;
    }

    public void setIssn(String issn) {
        this.issn = issn;
    }

    public NaplacujeClanarina getNaplacujeClanarina() {
        return naplacujeClanarina;
    }

    public void setNaplacujeClanarina(NaplacujeClanarina naplacujeClanarina) {
        this.naplacujeClanarina = naplacujeClanarina;
    }

    public List<Rad> getRadovi() {
        return radovi;
    }

    public void setRadovi(List<Rad> radovi) {
        this.radovi = radovi;
    }
}
