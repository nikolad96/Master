package com.example.naucnacentrala.dto;

public class NaucnaOblastDTO {

    private Integer id;
    private String naziv;

    public NaucnaOblastDTO() {
    }

    public NaucnaOblastDTO(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
}
