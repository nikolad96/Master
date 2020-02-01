package com.example.paypalservice.dto;

public class SubPomDTO {
    private Integer id;
    private String token;

    public SubPomDTO(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public SubPomDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
