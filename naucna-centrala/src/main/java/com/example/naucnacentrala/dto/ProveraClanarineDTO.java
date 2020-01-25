package com.example.naucnacentrala.dto;

public class ProveraClanarineDTO {

    private Integer id_korisnika;
    private Integer id_casopisa;

    public ProveraClanarineDTO() {
    }

    public ProveraClanarineDTO(Integer id_korisnika, Integer id_casopisa) {
        this.id_korisnika = id_korisnika;
        this.id_casopisa = id_casopisa;
    }

    public Integer getId_korisnika() {
        return id_korisnika;
    }

    public void setId_korisnika(Integer id_korisnika) {
        this.id_korisnika = id_korisnika;
    }

    public Integer getId_casopisa() {
        return id_casopisa;
    }

    public void setId_casopisa(Integer id_casopisa) {
        this.id_casopisa = id_casopisa;
    }

}
