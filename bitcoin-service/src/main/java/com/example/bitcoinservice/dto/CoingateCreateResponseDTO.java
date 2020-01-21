package com.example.bitcoinservice.dto;

public class CoingateCreateResponseDTO {
    private Integer id;
    private String status;
    private Boolean do_not_convert;
    private String price_currency;
    private String price_amount;
    private Boolean lightning_network;
    private String receive_currency;
    private String receive_amount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getDo_not_convert() {
        return do_not_convert;
    }

    public void setDo_not_convert(Boolean do_not_convert) {
        this.do_not_convert = do_not_convert;
    }

    public String getPrice_currency() {
        return price_currency;
    }

    public void setPrice_currency(String price_currency) {
        this.price_currency = price_currency;
    }

    public String getPrice_amount() {
        return price_amount;
    }

    public void setPrice_amount(String price_amount) {
        this.price_amount = price_amount;
    }

    public Boolean getLightning_network() {
        return lightning_network;
    }

    public void setLightning_network(Boolean lightning_network) {
        this.lightning_network = lightning_network;
    }

    public String getReceive_currency() {
        return receive_currency;
    }

    public void setReceive_currency(String receive_currency) {
        this.receive_currency = receive_currency;
    }

    public String getReceive_amount() {
        return receive_amount;
    }

    public void setReceive_amount(String receive_amount) {
        this.receive_amount = receive_amount;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPayment_url() {
        return payment_url;
    }

    public void setPayment_url(String payment_url) {
        this.payment_url = payment_url;
    }

    public String getUnderpaid_amount() {
        return underpaid_amount;
    }

    public void setUnderpaid_amount(String underpaid_amount) {
        this.underpaid_amount = underpaid_amount;
    }

    public String getOverpaid_amount() {
        return overpaid_amount;
    }

    public void setOverpaid_amount(String overpaid_amount) {
        this.overpaid_amount = overpaid_amount;
    }

    public Boolean getIs_refundable() {
        return is_refundable;
    }

    public void setIs_refundable(Boolean is_refundable) {
        this.is_refundable = is_refundable;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String created_at;
    private String order_id;
    private String payment_url;
    private String underpaid_amount;
    private String overpaid_amount;
    private Boolean is_refundable;
    private String token;
}
