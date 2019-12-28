import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {NCComponent} from '../app/nc/nc.component';
import {KPComponent} from '../app/kp/kp.component';
import {PaypalComponent} from "./component/paypal/paypal.component";
import {PaypalRedComponent} from "./component/paypal-red/paypal-red.component";
import {BankPageComponent} from "./component/bank-page/bank-page.component";
import {PaypalCancelComponent} from "./component/paypal-cancel/paypal-cancel.component";


const routes: Routes = [
  {path: 'NC', component: NCComponent},
  {path: 'KP-page', component: KPComponent},
  {path: 'paypal', component: PaypalComponent},
  {path: 'paypal/red', component: PaypalRedComponent},
  {path: 'paypal/cancel', component: PaypalCancelComponent},
  {path: 'bank-page', component: BankPageComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
