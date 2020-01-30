import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import { PaypalDTO } from '../model/PaypalDTO';


@Component({
  selector: 'app-paypal-red',
  templateUrl: './paypal-red.component.html',
  styleUrls: ['./paypal-red.component.css']
})
export class PaypalRedComponent implements OnInit {

  payerId: string;
  paymentId: string;
  url: string;
  paypalDTO: PaypalDTO;
  message: string;

  constructor(private route: ActivatedRoute, private http: HttpClient) {

  }

  ngOnInit() {
    this.message = "completing payment..."
    this.url = 'https://localhost:8087/';
    this.route.queryParams
      .subscribe(params => {
        console.log(params);
        this.paymentId = params.paymentId;
        this.payerId = params.PayerID;
        this.paypalDTO = new PaypalDTO();
        this.paypalDTO.payerId = this.payerId;
        this.paypalDTO.paymentId =  this.paymentId;
        console.log(this.payerId);
        console.log(this.paymentId);
      });


    this.completePayment(this.paymentId, this.payerId);
  }

  completePayment(paymentId, payerId) {
    return this.http.post(this.url + 'paypal/complete/payment' , this.paypalDTO )
      .subscribe((response: any) => {
        console.log(response);
        this.message = 'Payment successfull';
      },

      (err) => {
        console.log(err);
      });
  }


}