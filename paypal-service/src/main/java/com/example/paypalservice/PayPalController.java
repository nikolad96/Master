package com.example.paypalservice;

import com.paypal.api.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/paypal")
public class PayPalController {


    private final PayPalClient payPalClient;

    @Autowired
    PayPalController(PayPalClient payPalClient){
        this.payPalClient = payPalClient;
    }

    @PostMapping(value = "/payment")
    public ResponseEntity<PaymentResponseDTO> makePayment(@RequestBody PaymentPaypalDTO paymentPaypalDTO){
        System.out.println("USAO U PAYMENT");
        Map<String, Object> x = payPalClient.createPayment(paymentPaypalDTO);
        PaymentResponseDTO response = new PaymentResponseDTO();
        System.out.println("URL:::::");
        System.out.println(x.get("redirect_url").toString());
        response.setPaymentUrl(x.get("redirect_url").toString());
        return new ResponseEntity<PaymentResponseDTO>( response, HttpStatus.OK);
    }

    @PostMapping(value = "/complete/payment")
    public Map<String, Object> completePayment(@RequestBody PayPalDTO payPalDTO, HttpServletRequest request){
        return payPalClient.completePayment(payPalDTO,request);
    }

    @PostMapping(value = "/complete/cancel")
    public Map<String, Object> cancelPayment(){
        Map<String, Object> response = new HashMap();
        response.put("status", "failed");
        System.out.println("Transaction Canceled");
        return response;
    }

}

