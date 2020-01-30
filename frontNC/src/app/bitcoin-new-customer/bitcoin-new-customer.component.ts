import { Component, OnInit } from '@angular/core';
import {BitcoinService} from '../services/bitcoin/bitcoin.service';
import {ActivatedRoute} from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-bitcoin-new-customer',
  templateUrl: './bitcoin-new-customer.component.html',
  styleUrls: ['./bitcoin-new-customer.component.css']
})
export class BitcoinNewCustomerComponent implements OnInit {

  private id_seller;
  private form: FormGroup;

  constructor(private route: ActivatedRoute, private bitcoinService: BitcoinService) {
    this.route.params.subscribe(
      (params) => {
        this.id_seller = params.id_customer;
      }
    );

    this.form = new FormGroup({
      secret: new FormControl('', Validators.required)
    });

  }

  ngOnInit() {
  }

  onSubmit() {

    let dto: {id: number, secret: string};
    dto = {
      id: this.id_seller,
      secret: this.form.get('secret').value
    };

    this.bitcoinService.postCustomerData(dto).subscribe(
      (succes) => {
        window.location.href='casopis-placanje/'.concat(this.id_seller);
      },

      (err) => {
        console.log(err);
      }
    );
  }

}
