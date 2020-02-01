import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-paypal-cancel',
  templateUrl: './paypal-cancel.component.html',
  styleUrls: ['./paypal-cancel.component.css']
})
export class PaypalCancelComponent implements OnInit {

  url: string;
  paymentId: string;

  constructor( private route: ActivatedRoute, private http: HttpClient) { }

  ngOnInit() {
    this.url = 'https://localhost:8087/';
    this.cancelPayment();
    // this.route.params
    //     .subscribe(params => {
    //       console.log(params);
          
    //       this.paymentId =  params.payment_id;
    //       console.log(this.paymentId);
         
    //     });
  }



  cancelPayment() {
    return this.http.post(this.url + 'paypal/complete/cancel' , 'canceled')
      .subscribe(response => {
        console.log(response);
        setTimeout(function() {
          if (true) {
          }
        }, 5000);
        window.location.href = '/';
      });
  }
}
