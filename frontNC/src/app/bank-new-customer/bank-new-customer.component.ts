import { Component, OnInit } from '@angular/core';
import { BankServiceService } from '../services/bank-service/bank-service.service';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CustomerDTO } from '../model/CustomerDTO';

@Component({
  selector: 'app-bank-new-customer',
  templateUrl: './bank-new-customer.component.html',
  styleUrls: ['./bank-new-customer.component.css']
})
export class BankNewCustomerComponent implements OnInit {

  private id_customer: number;
  private form: FormGroup;
  private customerDTO: CustomerDTO;

  constructor(private route: ActivatedRoute, private bankServiceService: BankServiceService) {

    this.route.params.subscribe( params => {
      this.id_customer = params.id_customer;
    });

    this.form = new FormGroup({
      merchantId: new FormControl('', Validators.required  ),
      merchantPassword: new FormControl('', Validators.required  )
    });

   }


  ngOnInit() {
  }

  onSubmit() {

    this.customerDTO = new CustomerDTO();
    this.customerDTO.merchantId = this.form.get('merchantId').value;
    this.customerDTO.merchantPassword = this.form.get('merchantPassword').value;
    this.customerDTO.customerId = this.id_customer;

    console.log(this.customerDTO);

    this.bankServiceService.postCustomerData(this.customerDTO).subscribe(
      (success) => {
        console.log(success);
        alert('Uspesno ste dodali novi casopis');
        window.location.href='';
        
      },

      (err) => {
        console.log(err);
        console.log("desila se greska");
      }
    );

  }

}
