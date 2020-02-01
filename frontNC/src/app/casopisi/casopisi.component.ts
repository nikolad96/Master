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
  }

  subscribe(casopisId){
    window.location.href = '/plan/create/'.concat(casopisId);
  }
  preuzmiRad(radId){
    // TODO
    console.log('preuzmi rad: ' + radId);
  }

  nazad(){
    window.location.href='';
  }

}
