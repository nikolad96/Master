import { Component, OnInit } from '@angular/core';
import {HttpServiceService} from '../http-service.service';

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


  constructor(private service: HttpServiceService) { }

  ngOnInit() {
  }

  sendToBackend(option){
    switch(option) {
      case "bank":
        break;
      case "paypal":
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
          this.service.postMonitor('https://localhost:8086/bitcoin-service/bitcoin-service/monitor', this.order_id_btc)
        }
        break;
      default:

    }
  }

}
