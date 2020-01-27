import { Component, OnInit } from '@angular/core';
import { KpService} from '../services/kp/kp.service';
import {ActivatedRoute} from '@angular/router';
import { ConstantPool } from '@angular/compiler';

@Component({
  selector: 'app-odabir-placanja',
  templateUrl: './odabir-placanja.component.html',
  styleUrls: ['./odabir-placanja.component.css']
})
export class OdabirPlacanjaComponent implements OnInit {

  private casopis_id;
  private rad_id;
  private nacini_placanja = [];

  constructor(private kpService : KpService, private route : ActivatedRoute) {
    this.route.params.subscribe(
      (params) => {
        this.casopis_id = params.id_casopis;
        this.rad_id = params.id_rad;
      }
    );


    kpService.getNaciniPlacanja(this.casopis_id).subscribe(
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
          },
    
          (err) => {
            console.log(err);
          }
        );

        break;
      case '2':
        // paypal
        console.log('paypal');
        break;
      case '3':
        // bitcoin
        console.log('bitcoin');
        let transactionRequestDTO = {
          transaction_id : 1,
          // seller_id : this.trazeni_casopis.id,
          // seller_name : this.trazeni_casopis.name
        }
        break;
    }
  }

  odustani(){
    window.location.href = '/casopisi';
  }

}