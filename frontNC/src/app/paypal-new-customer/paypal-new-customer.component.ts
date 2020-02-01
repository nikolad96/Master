import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { BitcoinService } from '../services/bitcoin/bitcoin.service';
import { PaypalServiceService } from '../services/paypal-service.service';

@Component({
  selector: 'app-paypal-new-customer',
  templateUrl: './paypal-new-customer.component.html',
  styleUrls: ['./paypal-new-customer.component.css']
})
export class PaypalNewCustomerComponent implements OnInit {

 
  private id_seller;
  private form: FormGroup;

  constructor(private route: ActivatedRoute, private paypalService: PaypalServiceService) {
    this.route.params.subscribe(
      (params) => {
        this.id_seller = params.id_customer;
      }
    );

    this.form = new FormGroup({
      clientId: new FormControl('', Validators.required),
      clientSecret: new FormControl('', Validators.required)
    });

  }

  ngOnInit() {
  }

  onSubmit() {

    let dto: {id: number, clientSecret: string, clientId: string};
    dto = {
      id: this.id_seller,
      clientSecret: this.form.get('clientSecret').value,
      clientId: this.form.get('clientId').value
    };

    this.paypalService.postCustomerData(dto).subscribe(
      (succes) => {
        window.location.href='casopis-placanje/'.concat(this.id_seller);
      },

      (err) => {
        console.log(err);
      }
    );
  }


}
