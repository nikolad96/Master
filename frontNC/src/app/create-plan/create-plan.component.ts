import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { PaypalServiceService } from '../services/paypal-service.service';


@Component({
  selector: 'app-create-plan',
  templateUrl: './create-plan.component.html',
  styleUrls: ['./create-plan.component.css']
})
export class CreatePlanComponent implements OnInit {

 
  private id_seller;
  private form: FormGroup;

  constructor(private route: ActivatedRoute, private paypalService: PaypalServiceService) {
    this.route.params.subscribe(
      (params) => {
        this.id_seller = params.id_customer;
      }
    );

    this.form = new FormGroup({
      clientId: new FormControl(''),
      clientSecret: new FormControl('')
    });

  }

  ngOnInit() {
  }

  onSubmit() {

    let dto: {id: number, clientSecret: string, clientId: string};
    dto = {
      id: this.id_seller,
      clientSecret: 'asfasgasgasg',
      clientId: 'asfasgasgasg'
    };

    this.paypalService.createPlan(dto).subscribe(
      (success) => {
        console.log(success);
        window.location.href = success.redirect_url;
      },

      (err) => {
        console.log(err);
      }
    );
  }

}
