import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { RegistrationService } from '../services/registration/registration.service';

@Component({
  selector: 'app-activation',
  templateUrl: './activation.component.html',
  styleUrls: ['./activation.component.css']
})
export class ActivationComponent implements OnInit {

  private id_user: number;
  private id_process;

  constructor(private route: ActivatedRoute, private registrationService: RegistrationService) {
    this.route.params.subscribe( params => {
      this.id_user = params.id_user; 
      this.id_process = params.id_process;
    });
   }

  ngOnInit() {
  }

  activate(){
    let o = new Array();
    o.push({fieldId : "id_user", fieldValue : this.id_user});
    console.log(o);

    this.registrationService.activateUser(o, this.id_process).subscribe(
      res => {
        console.log("res", res);
        alert('Uspesno ste aktivirali nalog');
        window.location.href="";
    
      },
      err => {
        console.log("Error occured");
      }
    );
  }

}
