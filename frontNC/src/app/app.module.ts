import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule }   from '@angular/forms';
import { RouterModule, Routes } from '@angular/router';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ZuulPath } from './ZuulPath';
import { RegistrationComponent } from './registration/registration.component';
import { MainPageComponent } from './main-page/main-page.component';
import { ActivationComponent } from './activation/activation.component';
import { PotvrdaRecenzentiComponent } from './potvrda-recenzenti/potvrda-recenzenti.component';
import { LoginComponent } from './login/login.component';
import { JwtInterceptor } from './jwt.interceptor';
import { CasopisComponent } from './casopis/casopis.component';
import { UredniciRecenzentiComponent } from './urednici-recenzenti/urednici-recenzenti.component';
import { CasopisAdminComponent } from './casopis-admin/casopis-admin.component';
import { IspravkaComponent } from './ispravka/ispravka.component';
import { CasopisiComponent } from './casopisi/casopisi.component';
import { OdabirPlacanjaComponent } from './odabir-placanja/odabir-placanja.component';
import { BankPageComponent } from './bank-page/bank-page.component';
import { BankPath } from './BankPath';
import { CasopisPlacanjeComponent } from './casopis-placanje/casopis-placanje.component';
import { BankNewCustomerComponent } from './bank-new-customer/bank-new-customer.component';
import { BankServicePath } from './BankServicePath';

const Routes = [
  { path: "", component: MainPageComponent },
  { path: "registration", component: RegistrationComponent },
  { path: "activation/:id_user/:id_process", component: ActivationComponent },
  { path: "recenzenti", component: PotvrdaRecenzentiComponent },
  { path: "login", component: LoginComponent },
  { path: "casopis", component: CasopisComponent },
  { path: "urednici-recenzenti/:id_process", component: UredniciRecenzentiComponent },
  { path: "casopis-admin", component: CasopisAdminComponent },
  { path: "ispravka", component: IspravkaComponent },
  { path: "casopisi", component: CasopisiComponent },
  { path: "odabir_placanja/:id_casopis/:id_rad", component: OdabirPlacanjaComponent},
  { path: "bank-page/:id_payment", component: BankPageComponent },
  { path: "casopis-placanje/:id_casopis", component: CasopisPlacanjeComponent },
  { path: "bank-new-customer/:id_customer", component: BankNewCustomerComponent }
]

@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    MainPageComponent,
    ActivationComponent,
    PotvrdaRecenzentiComponent,
    LoginComponent,
    CasopisComponent,
    UredniciRecenzentiComponent,
    CasopisAdminComponent,
    IspravkaComponent,
    CasopisiComponent,
    OdabirPlacanjaComponent,
    BankPageComponent,
    CasopisPlacanjeComponent,
    BankNewCustomerComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }, ZuulPath, BankPath],
  bootstrap: [AppComponent]
})
export class AppModule { }
