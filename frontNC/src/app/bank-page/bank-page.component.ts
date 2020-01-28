import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CardRequestDTO } from '../model/CardRequestDTO';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { KpService } from '../services/kp/kp.service';

@Component({
  selector: 'app-bank-page',
  templateUrl: './bank-page.component.html',
  styleUrls: ['./bank-page.component.css']
})
export class BankPageComponent implements OnInit {

  form: FormGroup;
  cardRequest: CardRequestDTO;
  payment_id: number;

  constructor(private http: HttpClient, private route : ActivatedRoute, private kpService : KpService) {

    this.route.params.subscribe(
      (params) => {
        this.payment_id = params.id_payment;
      }
    );

    this.form = new FormGroup({
      pan: new FormControl('', Validators.required  ),
      cardholderName: new FormControl('', Validators.required  ),
      expirationDate: new FormControl('', Validators.required ),
      securityCode: new FormControl('', Validators.required ),
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.cardRequest = new CardRequestDTO();
    this.cardRequest.pan = this.form.get('pan').value;
    this.cardRequest.cardholderName = this.form.get('cardholderName').value;
    this.cardRequest.securityCode = this.form.get('securityCode').value;
    this.cardRequest.expirationDate = this.form.get('expirationDate').value;
    this.cardRequest.paymentId =  this.payment_id;

    console.log(this.cardRequest);

    this.kpService.checkAccount(this.cardRequest).subscribe(
      (success) => {
        console.log(success);

        if(success.state == "IN_PROCESS" && success.message == "Uspesna transakcija."){

          console.log('uspesna transakcija');

          this.kpService.executePayment(this.cardRequest).subscribe(
            (success) => {
              console.log(success);
              alert('Status vase transakcije: ' + success.state);
              window.location.href='';
            },
      
            (err) => {
              console.log(err);
            }
          );

        }else{
          console.log('greska u issuer');
          alert(success.message);
        }
      },

      (err) => {
        console.log(err);
        console.log("desila se greska");
        alert(err.error.message);
      }
    );

  }

}
