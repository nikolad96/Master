import { Component, OnInit } from '@angular/core';
import {HttpServiceService} from '../http-service.service';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-kp',
  templateUrl: './kp.component.html',
  styleUrls: ['./kp.component.css']
})
export class KPComponent implements OnInit {
  private responseObject;
  private orderStarted;
  private payment_url_btc;
  private order_id_btc;
  private payment_url_bank;
  private payment_id_bank;
  private merchant_order_id_bank;
  userModel: string;

  constructor(private service: HttpServiceService, private http: HttpClient) { }

  ngOnInit() {
  }

  sendToBackend(option){
    console.log(option);
    // window.location.href = 'paypal';

    switch(option) {
      case "bankSuccess":
           this.http.get('https://localhost:8084/dummyNC/paymentBankSuccess').subscribe(
          (success) => {
            console.log(success);
            window.location.href = 'bank-page';

          },
          (error) => {

          }
        );
        break;
      case "bankUnSuccess":
        break;
      case "paypal":
        window.location.href = 'paypal';
        break;
      case "btc":
        this.service.postStart('https://localhost:8086/bitcoin-service/bitcoin-service/start').subscribe(
          (success) => {
            this.responseObject = success;
            this.orderStarted = true;
            this.payment_url_btc = this.responseObject.get("payment_url");
            this.order_id_btc = this.responseObject.get("id");

          },
          (error) => {

          }
        );

        if(this.orderStarted) {
          this.service.postMonitor('https://localhost:8086/bitcoin-service/bitcoin-service/monitor', this.order_id_btc);
        }
        break;
      default:

    }
  }

}
