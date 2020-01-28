import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NCComponent } from './nc/nc.component';
import { KPComponent } from './kp/kp.component';
import { PaypalComponent} from "./component/paypal/paypal.component";
import {HttpClientModule} from "@angular/common/http";
import { PaypalRedComponent } from './component/paypal-red/paypal-red.component';
import { BankPageComponent } from './component/bank-page/bank-page.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {CommonModule} from "@angular/common";
import { PaypalCancelComponent } from './component/paypal-cancel/paypal-cancel.component';
import { TestComponent } from './component/test/test.component';


@NgModule({
  declarations: [
    AppComponent,
    NCComponent,
    KPComponent,
    PaypalComponent,
    PaypalRedComponent,
    BankPageComponent,
    PaypalCancelComponent,
    TestComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    CommonModule,
    FormsModule

  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
