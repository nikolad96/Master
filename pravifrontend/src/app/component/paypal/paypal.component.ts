import { Component, OnInit } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-paypal',
  templateUrl: './paypal.component.html',
  styleUrls: ['./paypal.component.css']
})
export class PaypalComponent implements OnInit {

  url: string;

  constructor(private http: HttpClient) {
  }

  ngOnInit() {
    this.url = 'https://localhost:8087/';
    // this.makePayment(5);
  }
  //
  // makePayment(sum) {
  //   return this.http.post(this.url + 'paypal/make/payment/' + sum, {})
  //     .subscribe(response => {
  //       console.log(response);
  //     });
  // }
}
