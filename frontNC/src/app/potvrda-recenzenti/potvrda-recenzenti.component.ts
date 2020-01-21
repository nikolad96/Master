import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../services/registration/registration.service';

@Component({
  selector: 'app-potvrda-recenzenti',
  templateUrl: './potvrda-recenzenti.component.html',
  styleUrls: ['./potvrda-recenzenti.component.css']
})
export class PotvrdaRecenzentiComponent implements OnInit {

  private tasks = [];
  
  constructor(private registrationService: RegistrationService) { }

  ngOnInit() {
    let x = this.registrationService.getTasks();

    x.subscribe(
      res => {
        console.log('res:', res);
        this.tasks = res;
      },
      err => {
        console.log("Error occured");
      }
    );
     
  }

  prihvati(taskId){
    console.log("prihvati: ", taskId);

    this.registrationService.recenzentPotvrda(taskId).subscribe(
      res => {
        alert("Korisnik uspesno potvrdjen kao recenzent");
        window.location.href="recenzenti";
    
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  odbij(taskId){
    console.log("odbij: ", taskId);

    this.registrationService.recenzentOdbijanje(taskId).subscribe(
      res => {
        alert("Korisnik odbijen kao recenzent");
        window.location.href="recenzenti";
    
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  nazad(){
    window.location.href = "";
  }
  

}
