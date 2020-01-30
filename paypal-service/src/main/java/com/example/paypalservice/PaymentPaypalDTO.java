package com.example.paypalservice;

public class PaymentPaypalDTO {

    private Integer seller_id;
    private String seller_name;
    private Integer buyer_id;
    private String buyer_name;
    private Double amount;
    private Integer rad_id;


    public Integer getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(Integer seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public Integer getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(Integer buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getBuyer_name() {
        return buyer_name;
    }

    public void setBuyer_name(String buyer_name) {
        this.buyer_name = buyer_name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getRad_id() {
        return rad_id;
    }

    public void setRad_id(Integer rad_id) {
        this.rad_id = rad_id;
    }

    public PaymentPaypalDTO() {
    }

    public PaymentPaypalDTO(Integer seller_id, String seller_name, Integer buyer_id, String buyer_name, Double amount, Integer rad_id) {

        this.seller_id = seller_id;
        this.seller_name = seller_name;
        this.buyer_id = buyer_id;
        this.buyer_name = buyer_name;
        this.amount = amount;
        this.rad_id = rad_id;
    }
}
