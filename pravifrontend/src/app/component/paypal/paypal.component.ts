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
    this.url = 'http://localhost:8087/';

  }

  makePayment(sum) {
    return this.http.post(this.url + 'paypal/make/payment/' + sum, {})
      .subscribe((response: any) => {
        console.log(response);
        window.location.href = response.redirect_url;
      });
  }

  onSubmit() {
    this.makePayment(5);
  }
}
