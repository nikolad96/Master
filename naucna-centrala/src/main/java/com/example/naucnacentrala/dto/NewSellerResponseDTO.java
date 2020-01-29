package com.example.naucnacentrala.dto;

public class NewSellerResponseDTO {

    private String redirectionUrl;

    public NewSellerResponseDTO() {
    }

    public NewSellerResponseDTO(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }

    public String getRedirectionUrl() {
        return redirectionUrl;
    }

    public void setRedirectionUrl(String redirectionUrl) {
        this.redirectionUrl = redirectionUrl;
    }
}
