import { Component, OnInit } from '@angular/core';
import { KpService} from '../services/kp/kp.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-odabir-placanja',
  templateUrl: './odabir-placanja.component.html',
  styleUrls: ['./odabir-placanja.component.css']
})
export class OdabirPlacanjaComponent implements OnInit {

  private casopis_id;
  private rad_id;
  private casopisi = [];
  private trazeni_casopis;
  private nacini_placanja = [];

  constructor(private kpService : KpService, private route : ActivatedRoute) {
    this.route.params.subscribe(
      (params) => {
        this.casopis_id = params.id_casopis;
        this.rad_id = params.id_rad;
      }
    );

    kpService.getCasopisi().subscribe(
      (success) => {
        //console.log(success);
        this.casopisi = success;

        for(let casopis of this.casopisi) {
          console.log(casopis);
          if(casopis.id == this.casopis_id ){
            this.trazeni_casopis = casopis;
            console.log(this.trazeni_casopis);
          }
        }

        kpService.getNaciniPlacanja(this.trazeni_casopis).subscribe(
          (success) => {
            this.nacini_placanja = success;
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







  }

  ngOnInit() {
  }

  placanje(nacinPlacanja){
    switch(nacinPlacanja){
      case "BANK":
        break;
      case "PAYPAL":
        break;
      case "BITCOIN":
        let transactionRequestDTO = {
          transaction_id : 1,
          seller_id : this.trazeni_casopis.id,
          seller_name : this.trazeni_casopis.name
        }
        break;
    }
  }

  odustani(){
    window.location.href = '/casopisi';
  }

}
