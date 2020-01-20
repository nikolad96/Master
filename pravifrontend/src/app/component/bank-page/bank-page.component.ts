import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient} from "@angular/common/http";
import {CardRequestDTO} from "../../model/CardRequestDTO";

@Component({
  selector: 'app-bank-page',
  templateUrl: './bank-page.component.html',
  styleUrls: ['./bank-page.component.css']
})
export class BankPageComponent implements OnInit {

  form: FormGroup;
  cardRequest: CardRequestDTO;


  constructor(private http: HttpClient) {
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

    this.cardRequest.paymentId =  1;
    this.cardRequest.merchantOrderId = 1;
    return this.http.post('https://localhost:8082/bank/checkAccount' , this.cardRequest )
      .subscribe(response => {
        console.log(response);

      });
  }
}
