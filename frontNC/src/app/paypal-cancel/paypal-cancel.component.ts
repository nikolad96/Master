import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-paypal-cancel',
  templateUrl: './paypal-cancel.component.html',
  styleUrls: ['./paypal-cancel.component.css']
})
export class PaypalCancelComponent implements OnInit {

  url: string;

  constructor( private http: HttpClient) { }

  ngOnInit() {
    this.url = 'https://localhost:8087/';
    this.cancelPayment();
  }

  cancelPayment() {
    return this.http.post(this.url + 'paypal/complete/cancel' , 'canceled')
      .subscribe(response => {
        console.log(response);
        setTimeout(function() {
          if (true) {
          }
        }, 5000);
        window.location.href = 'paypal';
      });
  }
}
