import { Component, OnInit } from '@angular/core';
import { KpService } from '../services/kp/kp.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-casopis-placanje',
  templateUrl: './casopis-placanje.component.html',
  styleUrls: ['./casopis-placanje.component.css']
})
export class CasopisPlacanjeComponent implements OnInit {

  private nacini_placanja = [];
  private form: FormGroup;
  private id_casopis: number;

  constructor(private kpService: KpService, private route: ActivatedRoute) {

    this.route.params.subscribe( params => {
      this.id_casopis = params.id_casopis;
    });

    console.log('casopisId: ' + this.id_casopis);

    kpService.getNaciniPlacanja().subscribe(
      res => {
        console.log(res);
        this.nacini_placanja = res;

      },
      err => {
        console.log("Error occured");
      }
    );

    this.form = new FormGroup({
      placanje: new FormControl('', Validators.required  )
    });

   }

  ngOnInit() {
  }

  onSubmit() {
    
    console.log(this.form.get('placanje').value);

    this.kpService.noviCasopis(this.id_casopis, this.form.get('placanje').value).subscribe(
      (success) => {
        console.log(success);

        if(success.redirectionUrl != ''){
          window.location.href = success.redirectionUrl + '/' + success.customerId;
        }else{
          alert('Odabrani nacin placanja je vec zabelezen');
        }
      },

      (err) => {
        console.log(err);
        console.log("desila se greska");
      }
    );

  }

  nazad(){
    window.location.href = '';
  }

}
