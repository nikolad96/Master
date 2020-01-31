import { Component, OnInit } from '@angular/core';
import { KpService} from '../services/kp/kp.service';
import {ActivatedRoute} from '@angular/router';
import { ConstantPool } from '@angular/compiler';
import {AuthService} from '../services/auth/auth.service';

@Component({
  selector: 'app-odabir-placanja',
  templateUrl: './odabir-placanja.component.html',
  styleUrls: ['./odabir-placanja.component.css']
})
export class OdabirPlacanjaComponent implements OnInit {

  private casopis_id;
  private rad_id;
  private nacini_placanja = [];

  constructor(private kpService : KpService, private route : ActivatedRoute, private authService : AuthService) {
    this.route.params.subscribe(
      (params) => {
        this.casopis_id = params.id_casopis;
        this.rad_id = params.id_rad;
      }
    );


    kpService.getNaciniPlacanjaCasopis(this.casopis_id).subscribe(
      (success) => {
        console.log(success);
        this.nacini_placanja = success;
      },

      (err) => {
        console.log(err);
      }
    );


  }

  ngOnInit() {
  }

  kupi(nacinPlacanjaId){
    console.log('nacinPlacanjaId: ' + nacinPlacanjaId);

    switch(nacinPlacanjaId){
      case '1':
        // bank
        console.log('bank');

        this.kpService.placanjeBanka(this.rad_id, this.casopis_id).subscribe(
          (success) => {
            console.log(success);
            console.log('paymentUrl:' + success.paymentUrl);
            window.location.href = success.paymentUrl + '/' + success.paymentId;
          },

          (err) => {
            console.log(err);
          }
        );

        break;
      case '2':
        // paypal
        console.log('paypal');
        this.kpService.placanjePaypal(this.rad_id, this.casopis_id).subscribe(
          (success) => {
            console.log(success);
            console.log('paymentUrl:' + success.paymentUrl);
            window.location.href = success.paymentUrl ;
          }
        );
        break;
      case '3':
        // bitcoin
        console.log('bitcoin');
        let user: any = this.authService.getUser().subscribe(
          (success) => {
            this.kpService.placanjeBitcoin(this.rad_id, this.casopis_id, success.username).subscribe(
              (success2) => {
                console.log(success2);
                window.location.href = success2.paymentUrl;
              },

              (err) => {
                console.log(err);
              }
            );
          },

          (err) => {
            console.log(err);
          }
        );

        break;
    }
  }

  odustani(){
    window.location.href = '/casopisi';
  }

}
