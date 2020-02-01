package com.example.paypalservice;


import com.example.paypalservice.dto.*;
import com.example.paypalservice.model.PaypalMerchant;
import com.example.paypalservice.model.PaypalTransaction;
import com.example.paypalservice.model.Subscription;
import com.example.paypalservice.model.TransactionStatus;
import com.example.paypalservice.repositorium.MerchantRepositorium;
import com.example.paypalservice.repositorium.TransactionRepositorium;
import com.example.paypalservice.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(value = "/paypal")
public class PayPalController {


    private final PayPalClient payPalClient;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    TransactionRepositorium transactionRepositorium;

    @Autowired
    MerchantRepositorium merchantRepositorium;

    @Autowired
    TransactionService transactionService;

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

    @PostMapping(value = "/complete/payment/{radId}")
    public Map<String, Object> completePayment(@RequestBody PayPalDTO payPalDTO,@PathVariable("radId") Integer radId, HttpServletRequest request){
        Map<String, Object> x = new HashMap<>();
        x = payPalClient.completePayment(payPalDTO,request);
        System.out.println("USAO U COMPLETE PAYMENT");
        ResponseEntity<String> subResponse = restTemplate.getForEntity("https://localhost:8096/KP/paypalPaid/".concat(radId.toString()),  String.class);

        return x;
    }


    @RequestMapping(value = "/newSeller", method = RequestMethod.POST)
    public ResponseEntity<CustomerResponseDTO> newSeller( @RequestBody CustomerRequestDTO dto){

       PaypalMerchant m = new PaypalMerchant();
       m.setId(dto.getSellerId());
       m.setSellerId(dto.getSellerId());

        merchantRepositorium.save(m);

        return new ResponseEntity<CustomerResponseDTO>(new CustomerResponseDTO("paypal-new-customer", m.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/newSellerData", method = RequestMethod.POST)
    public ResponseEntity<String> newSellerData(@RequestBody SellerDTO dto){
        PaypalMerchant m = merchantRepositorium.findOneBySellerId(dto.getId());
        m.setClientId(dto.getClientId());
        m.setClientSecret(dto.getClientSecret());

        merchantRepositorium.save(m);

        updateSeller(m);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/createPlan", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> createPlan(@RequestBody SellerDTO dto){
        PaypalMerchant m = merchantRepositorium.findOneBySellerId(dto.getId());
        String s = payPalClient.createAndActivatePlan(2);
        String redirectUrl = payPalClient.createFinalAgreement(2,s);
        Map<String, Object> response = new HashMap<>();
        response.put("redirect_url", redirectUrl);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void updateSeller(PaypalMerchant merchant) {
        HttpEntity<UpdateSellerDTO> entity = new HttpEntity<UpdateSellerDTO>(new UpdateSellerDTO(merchant.getSellerId(), "paypal"));
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://localhost:8091/sellers/updateSeller",
                HttpMethod.PUT, entity, String.class);
    }
    @RequestMapping(value = "/executeSubscription", method = RequestMethod.POST)
    public ResponseEntity<Map<String, Object>> executeSubscription(HttpServletRequest request, @RequestBody SubPomDTO subPomDTO){
        PaypalMerchant m = merchantRepositorium.findOneBySellerId(subPomDTO.getId());
        Map<String, Object> response = new HashMap();
        response.put("status", "Successfully subscribed");
        System.out.println(request.getHeaderNames());
        String username ="NOT_USED";
        Subscription s = payPalClient.executeAgreement(subPomDTO.getToken(), subPomDTO.getId(), username);
        //Send subscription to KP

        SubscriptionDTO subDTO = new SubscriptionDTO(s.getId(), s.getSellerId(), s.getStartDate(), s.getUsername(), s.getEndDate(), s.getState(),s.getDescription());

        ResponseEntity<SubscriptionDTO> subResponse = restTemplate.postForEntity("https://localhost:8096/KP/sendSubscription", subDTO, SubscriptionDTO.class);

        return new ResponseEntity<>(  response,HttpStatus.OK);
    }

    @PostMapping(value = "/complete/cancel")
    public Map<String, Object> cancelPayment(){
        Map<String, Object> response = new HashMap();
        response.put("status", "failed");
        System.out.println("Transaction Canceled");
//        PaypalTransaction paypalTransaction = new PaypalTransaction();
//        paypalTransaction = transactionService.findOneByPaymentId(paymentId);
//        paypalTransaction.setTransactionStatus(TransactionStatus.CANCELLED);
//        transactionRepositorium.save(paypalTransaction);

        return response;
    }

}

