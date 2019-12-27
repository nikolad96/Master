import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {NCComponent} from '../app/nc/nc.component';
import {KPComponent} from '../app/kp/kp.component';
import {PaypalComponent} from "./component/paypal/paypal.component";


const routes: Routes = [
  {path: '',
   component: NCComponent}
   ,
   {path: 'KP-page',
    component: KPComponent},
  {path: 'paypal', component: PaypalComponent}
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
