package com.example.paypalservice;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import com.example.paypalservice.dto.SellerDTO;
import com.example.paypalservice.model.PaypalMerchant;
import com.example.paypalservice.model.PaypalTransaction;
import com.example.paypalservice.model.Subscription;
import com.example.paypalservice.model.TransactionStatus;
import com.example.paypalservice.repositorium.MerchantRepositorium;
import com.example.paypalservice.repositorium.SubscriptionRepositorium;
import com.example.paypalservice.repositorium.TransactionRepositorium;
import com.example.paypalservice.service.TransactionService;
import com.paypal.api.payments.*;
import com.paypal.api.payments.Currency;
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

    @Autowired
    MerchantRepositorium merchantRepositorium;

    @Autowired
    SubscriptionRepositorium subscriptionRepositorium;

    String clientId = "AcRXzhEMmFYWTWl9qfPnVoTK4iOZjJEq-XM6NfYGP4B-zMkXCiZuxawgWzp8wTHZITgUACSG5Yni8cxP";
    String clientSecret = "EDF_JuZVtbK3acUiDrkRAg-RmmMjtDocJPZoDNcqrIDHqZ2Q-C98FW3bAiNfMgrUpQ5sWco1n5epcA2_";

    public Map<String, Object> createPayment(PaymentPaypalDTO paymentPaypalDTO){

        System.out.println(paymentPaypalDTO.getSeller_id());


        Map<String, Object> response = new HashMap<String, Object>();
        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(paymentPaypalDTO.getAmount().toString());
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
        redirectUrls.setReturnUrl("https://localhost:4200/paypal/red/".concat(paymentPaypalDTO.getRad_id().toString()));
        payment.setRedirectUrls(redirectUrls);
        Payment createdPayment;



        PaypalTransaction paypalTransaction = new PaypalTransaction();
        try {
            String redirectUrl = "";
            PaypalMerchant m = merchantRepositorium.findOneBySellerId(paymentPaypalDTO.getSeller_id());
            APIContext context = new APIContext(m.getClientId(), m.getClientSecret(), "sandbox");
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

                final PaypalTransaction finalTransaction = paypalTransaction;
                CompletableFuture<Object> completableFuture = CompletableFuture.supplyAsync(() -> {

                    Timer timer = new Timer();
                    long pocetak = System.currentTimeMillis();

                    //na svakih 10 sekundi proverava da li se promenio status transakcije
                    //nakon 5min, transakcija automatski postaje neuspesna
                    timer.schedule(new TimerTask(){

                        @Override
                        public void run(){

                            System.out.println("--pocetak provere--");
                            long trenutno = System.currentTimeMillis();
                            PaypalTransaction transactionHelp = transactionRepositorium.findOneById(finalTransaction.getId());

                            if(!transactionHelp.getTransactionStatus().equals(TransactionStatus.PENDING)){
                                System.out.println("--transakcija je uspesna--");
                                timer.cancel();
                            }


                            if(trenutno - pocetak > 300000){
                                System.out.println("--proslo 5min -> neuspesna transakcija--");
                                transactionHelp.setTransactionStatus(TransactionStatus.CANCELLED);
                                transactionHelp = transactionRepositorium.save(transactionHelp);
                                timer.cancel();
                            }
                        }
                    },0,10000);

                    return "end";
                });

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





        payment.setIntent("sale");
        payment.setId(payPalDTO.getPaymentId());
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payPalDTO.getPayerId());
        Payment createdPayment = new Payment();
        try {
            APIContext context = new APIContext(clientId, clientSecret, "sandbox");
            System.out.println("pre");
            createdPayment = payment.execute(context, paymentExecution);
            PaypalTransaction paypalTransaction = new PaypalTransaction();
            paypalTransaction = transactionService.findOneByPaymentId(payPalDTO.getPaymentId());
            paypalTransaction.setTransactionStatus(TransactionStatus.PAID);
            transactionRepositorium.save(paypalTransaction);
            System.out.println("posle");
            if(createdPayment!=null){

                response.put("status", "success");

//                response.put("payment", createdPayment);
                System.out.println("Uspesno Prosla Transakcija");
            }
        } catch (PayPalRESTException e) {
            PaypalTransaction paypalTransaction = new PaypalTransaction();
            paypalTransaction = transactionService.findOneByPaymentId(payPalDTO.getPaymentId());
            paypalTransaction.setTransactionStatus(TransactionStatus.CANCELLED);
            transactionRepositorium.save(paypalTransaction);
            System.err.println(e.getDetails());
        }

        return response;
    }


    public Plan createBillingPlan(Integer sellerId){
        Plan plan = new Plan();
        plan.setName("Mesecna pretplata na casopis");
        plan.setDescription("Opis plana");
        plan.setType("fixed");

        PaymentDefinition paymentDefinition = new PaymentDefinition();
        paymentDefinition.setName("Regular Payments");
        paymentDefinition.setType("REGULAR");
        paymentDefinition.setFrequency("MONTH");
        paymentDefinition.setFrequencyInterval("1");
        paymentDefinition.setCycles("6");


        Currency currency = new Currency();
        currency.setCurrency("USD");
        currency.setValue("10");
        paymentDefinition.setAmount(currency);

        ChargeModels chargeModels = new com.paypal.api.payments.ChargeModels();
        chargeModels.setType("SHIPPING");
        chargeModels.setAmount(currency);
        List<ChargeModels> chargeModelsList = new ArrayList<ChargeModels>();
        chargeModelsList.add(chargeModels);
        paymentDefinition.setChargeModels(chargeModelsList);


        List<PaymentDefinition> paymentDefinitionList = new ArrayList<PaymentDefinition>();
        paymentDefinitionList.add(paymentDefinition);
        plan.setPaymentDefinitions(paymentDefinitionList);


        MerchantPreferences merchantPreferences = new MerchantPreferences();
        merchantPreferences.setSetupFee(currency);
        merchantPreferences.setCancelUrl("https://localhost:4200/sub/cancel");
        merchantPreferences.setReturnUrl("https://localhost:4200/sub/return/"+ sellerId);
        merchantPreferences.setMaxFailAttempts("0");
        merchantPreferences.setAutoBillAmount("YES");
        merchantPreferences.setInitialFailAmountAction("CONTINUE");
        plan.setMerchantPreferences(merchantPreferences);

        return plan;
    }

    public String createAndActivatePlan(Integer sellerId) {
        Plan plan = createBillingPlan(sellerId);
        PaypalMerchant m = merchantRepositorium.findOneBySellerId(sellerId);
        APIContext apiContext = new APIContext(m.getClientId(), m.getClientSecret(), "sandbox");

        try {
            // Create payment
            Plan createdPlan = plan.create(apiContext);
            System.out.println("Created plan with id = " + createdPlan.getId());
            System.out.println("Plan state = " + createdPlan.getState());

            // Set up plan activate PATCH request
            List<Patch> patchRequestList = new ArrayList<Patch>();
            Map<String, String> value = new HashMap<String, String>();
            value.put("state", "ACTIVE");

            // Create update object to activate plan
            Patch patch = new Patch();
            patch.setPath("/");
            patch.setValue(value);
            patch.setOp("replace");
            patchRequestList.add(patch);

            // Activate plan
            createdPlan.update(apiContext, patchRequestList);
            System.out.println("Plan state = " + createdPlan.getState());
            return createdPlan.getId();
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
            return null;
        }

    }




    public String getDate() {
        Date d = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 5);
//        d = calendar.getTime();
        Integer day = calendar.get(Calendar.DATE);
        Integer month = calendar.get(Calendar.MONTH);
        month = month + 1;
        Integer year = calendar.get(Calendar.YEAR);
        Integer hours = calendar.get(Calendar.HOUR_OF_DAY);
        Integer minutes = calendar.get(Calendar.MINUTE);
        Integer seconds = calendar.get(Calendar.SECOND);

        String startDate = "";
        startDate = startDate.concat(year.toString()).concat("-");
        if (month < 10)
            startDate = startDate.concat("0").concat(month.toString()).concat("-");
        else
            startDate = startDate.concat(month.toString()).concat("-");
        if (day < 10)
            startDate = startDate.concat("0").concat(day.toString()).concat("T").concat(hours.toString()).concat(":");
        else
            startDate = startDate.concat(day.toString()).concat("T").concat(hours.toString()).concat(":");
        if (minutes < 10)
            startDate = startDate.concat("0").concat(minutes.toString()).concat(":");
        else
            startDate = startDate.concat(minutes.toString()).concat(":");

        if (seconds < 10)
            startDate = startDate.concat("0").concat(seconds.toString()).concat("Z");
        else
            startDate = startDate.concat(seconds.toString()).concat("Z");
        System.out.println(startDate);
        return  startDate;
    }
    public Agreement createBillingAgreement(String planId) {
        // Create new agreement
        Agreement agreement = new Agreement();
        agreement.setName("Base Agreement");
        agreement.setDescription("Basic Agreement");
        agreement.setStartDate(getDate());



// Set plan ID
        Plan plan = new Plan();
        plan.setId(planId);
        agreement.setPlan(plan);

// Add payer details
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");
        agreement.setPayer(payer);

// Set shipping address information
        ShippingAddress shipping = new ShippingAddress();
        shipping.setLine1("111 First Street");
        shipping.setCity("Saratoga");
        shipping.setState("CA");
        shipping.setPostalCode("95070");
        shipping.setCountryCode("US");
        agreement.setShippingAddress(shipping);

        return agreement;
    }

    public String createFinalAgreement(Integer sellerId, String planId) {
        try {
            Agreement agreement = createBillingAgreement(planId);

            PaypalMerchant m = merchantRepositorium.findOneBySellerId(sellerId);

            APIContext apiContext = new APIContext(m.getClientId(), m.getClientSecret(), "sandbox");
            System.out.println(agreement.getStartDate());
            agreement = agreement.create(apiContext);

            for (Links links : agreement.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    URL url = new URL(links.getHref());
                    System.out.println(url);
                    //REDIRECT USER TO url
                    return url.toString();
                }
            }
        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    return null;
    }
    public Subscription executeAgreement(String token, Integer sellerId, String username){
        Agreement agreement =  new Agreement();
        agreement.setToken(token);
        PaypalMerchant m = merchantRepositorium.findOneBySellerId(sellerId);
        APIContext apiContext = new APIContext(m.getClientId(), m.getClientSecret(), "sandbox");
        try {
            Agreement activeAgreement = agreement.execute(apiContext, agreement.getToken());
            System.out.println("Agreement created with ID " + activeAgreement.getId());
            Subscription subscription = new Subscription();
            subscription.setDescription(activeAgreement.getDescription());
            subscription.setUsername(username);
            subscription.setSellerId(sellerId);
            subscription.setState("Paid");
            subscription.setStartDate(activeAgreement.getStartDate());
            subscription.setEndDate(activeAgreement.getAgreementDetails().getFinalPaymentDate());
            subscriptionRepositorium.save(subscription);
            System.out.println(activeAgreement.getPayer().getPayerInfo().getPayerId());
            System.out.println("Agreement saved with ID " + activeAgreement.getId());
            return subscription;

        } catch (PayPalRESTException e) {
            System.err.println(e.getDetails());
            return null;
        }
    }


}
