import { Component, OnInit } from '@angular/core';
import { PaypalDTO } from '../model/PaypalDTO';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-sub-return',
  templateUrl: './sub-return.component.html',
  styleUrls: ['./sub-return.component.css']
})
export class SubReturnComponent implements OnInit {

  sellerId: number;
  payerId: string;
  paymentId: string;
  url: string;
  paypalDTO: PaypalDTO;
  message: string;
  token:string;

  constructor(private route: ActivatedRoute, private http: HttpClient) { }

  ngOnInit() {
    this.url = 'https://localhost:8087/';
    this.route.queryParams
      .subscribe(params => {
        console.log(params);
        this.token = params.token;
        console.log(this.route);
      });

      this.route.params
      .subscribe(params => {
        console.log(params);
        this.sellerId = params.sellerId;
        console.log(this.sellerId);
      });


    this.completeSub(this.token);
  }

  completeSub(token) {
    let dto: {id: number, token: string};
    dto = {
      id: this.sellerId,
      token: token
    };
    return this.http.post(this.url + 'paypal/executeSubscription' , dto )
      .subscribe((response: any) => {
        console.log(response);
        this.message = 'Subscription successfull';
      },

      (err) => {
        console.log(err);
      });
  }
  redirect(){
    window.location.href = '/';
  }

}
