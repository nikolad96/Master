package com.example.naucnacentrala.dto;

import com.example.naucnacentrala.model.NaplacujeClanarina;
import com.example.naucnacentrala.model.Rad;

import java.util.List;

public class CasopisDTO {

    private Integer id;
    private String name;
    private String issn;
    private NaplacujeClanarina naplacujeClanarina;
    private List<RadDTO> radovi;

    public CasopisDTO() {
    }

    public CasopisDTO(Integer id, String name, String issn, NaplacujeClanarina naplacujeClanarina) {
        this.id = id;
        this.name = name;
        this.issn = issn;
        this.naplacujeClanarina = naplacujeClanarina;
    }

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

    public void setNaplacujeClanarina(NaplacujeClanarina naplacujeClanarina) { this.naplacujeClanarina = naplacujeClanarina; }

    public List<RadDTO> getRadovi() {
        return radovi;
    }

    public void setRadovi(List<RadDTO> radovi) {
        this.radovi = radovi;
    }
}
