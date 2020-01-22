package com.example.paymentcardcenter.dto;

public class CardRequestDTO {

    private String pan;
    private String securityCode;
    private String cardholderName;
    private String expirationDate;
    private Integer paymentId;

    public CardRequestDTO() {
    }

    public CardRequestDTO(String pan, String securityCode, String cardholderName, String expirationDate, Integer paymentId) {
        this.pan = pan;
        this.securityCode = securityCode;
        this.cardholderName = cardholderName;
        this.expirationDate = expirationDate;
        this.paymentId = paymentId;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public void setCardholderName(String cardholderName) {
        this.cardholderName = cardholderName;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
}
