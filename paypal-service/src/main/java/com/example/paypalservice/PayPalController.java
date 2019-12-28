package com.example.paypalservice;

import com.paypal.api.payments.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/paypal")
public class PayPalController {


    private final PayPalClient payPalClient;

    @Autowired
    PayPalController(PayPalClient payPalClient){
        this.payPalClient = payPalClient;
    }

    @PostMapping(value = "/make/payment/{sum}")
    public Map<String, Object> makePayment(@PathVariable String sum){
        return payPalClient.createPayment(sum);
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

