package com.example.paypalservice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.paypalservice.model.PaypalTransaction;
import com.example.paypalservice.model.TransactionStatus;
import com.example.paypalservice.repositorium.TransactionRepositorium;
import com.example.paypalservice.service.TransactionService;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PayPalClient {

    @Autowired
    TransactionRepositorium transactionRepositorium;

    @Autowired
    TransactionService transactionService;

    String clientId = "AcRXzhEMmFYWTWl9qfPnVoTK4iOZjJEq-XM6NfYGP4B-zMkXCiZuxawgWzp8wTHZITgUACSG5Yni8cxP";
    String clientSecret = "EDF_JuZVtbK3acUiDrkRAg-RmmMjtDocJPZoDNcqrIDHqZ2Q-C98FW3bAiNfMgrUpQ5sWco1n5epcA2_";

    public Map<String, Object> createPayment(PaymentPaypalDTO paymentPaypalDTO){
        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("5");
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("https://localhost:4200/paypal/cancel");
        redirectUrls.setReturnUrl("https://localhost:4200/paypal/red");
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;

        PaypalTransaction paypalTransaction = new PaypalTransaction();
        try {
            String redirectUrl = "";
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            createdPayment = payment.create(context);

            if(createdPayment!=null){
                //save transaction
                paypalTransaction.setAmount(paymentPaypalDTO.getAmount());
                paypalTransaction.setBuyer_id(paymentPaypalDTO.getBuyer_id());
                paypalTransaction.setBuyer_name(paymentPaypalDTO.getBuyer_name());
                paypalTransaction.setSeller_id(paymentPaypalDTO.getSeller_id());
                paypalTransaction.setSeller_name(paymentPaypalDTO.getSeller_name());
                paypalTransaction.setTransactionStatus(TransactionStatus.PENDING);
                paypalTransaction.setPaymentId(createdPayment.getId());
                transactionRepositorium.save(paypalTransaction);

                //saved
                List<Links> links = createdPayment.getLinks();
                System.out.println(links);
                for (Links link:links) {
                    if(link.getRel().equals("approval_url")){
                        redirectUrl = link.getHref();
                        System.out.println(redirectUrl);
                        break;
                    }
                }
                response.put("status", "success");
                response.put("redirect_url", redirectUrl);
            }
        } catch (PayPalRESTException e) {
            System.out.println("Neuspesno Kreiranje Payment-a");
        }
        return response;
    }
    public Map<String, Object> completePayment(PayPalDTO payPalDTO, HttpServletRequest req){
        Map<String, Object> response = new HashMap();
        Payment payment = new Payment();

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal("5");
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        List<Transaction> transactions = new ArrayList<Transaction>();
        transactions.add(transaction);

        payment.setTransactions(transactions);


        PaypalTransaction paypalTransaction = new PaypalTransaction();
        paypalTransaction = transactionService.findOneByPaymentId(payPalDTO.getPaymentId());
        paypalTransaction.setTransactionStatus(TransactionStatus.PAID);
        transactionRepositorium.save(paypalTransaction);


        payment.setIntent("sale");
        payment.setId(payPalDTO.getPaymentId());
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payPalDTO.getPayerId());
        Payment createdPayment = new Payment();
        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            System.out.println("pre");
            createdPayment = payment.execute(context, paymentExecution);
            System.out.println("posle");
            if(createdPayment!=null){

                response.put("status", "success");

//                response.put("payment", createdPayment);
                System.out.println("Uspesno Prosla Transakcija");
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        }

        return response;
    }
}
