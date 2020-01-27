import { Component, OnInit } from '@angular/core';
import { KpService } from '../services/kp/kp.service';

@Component({
  selector: 'app-casopisi',
  templateUrl: './casopisi.component.html',
  styleUrls: ['./casopisi.component.css']
})
export class CasopisiComponent implements OnInit {

  private casopisi = [];
  private nacini_placanja = [];
  private izabrani_rad_id;
  private dobijeni_nacini_placanja = false;

  constructor(private kpService: KpService) {
    console.log('tu sam');

    kpService.getCasopisi().subscribe(
      res => {
        console.log(res);
        this.casopisi = res;

      },
      err => {
        console.log("Error occured");
      }
    );
   }


  ngOnInit() {
  }

  kupiRad(casopisId, radId){
    console.log('radId: ' + radId);

    window.location.href = '/odabir_placanja'.concat('/' + casopisId + '/' + radId);


    // TODO ovde dalje ubaciti da proveri koji nacini placanja su podrzani za casopis
    // pa korisniku staviti npr select da izabere jedan od tih podrzanih placanja
  }

  nazad(){
    window.location.href='';
  }

  preuzmiRad(radId){
    // TODO
  }

}
