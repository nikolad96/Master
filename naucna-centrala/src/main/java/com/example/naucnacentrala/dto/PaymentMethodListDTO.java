package com.example.naucnacentrala.dto;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodListDTO {

    private List<PaymentMethodDTO> methods;

    public PaymentMethodListDTO() {
        methods = new ArrayList<>();
    }

    public List<PaymentMethodDTO> getMethods() {
        return methods;
    }

    public void setMethods(List<PaymentMethodDTO> methods) {
        this.methods = methods;
    }
}
