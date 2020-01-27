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
  { path: "odabir_placanja/:id_casopis/:id_rad", component: OdabirPlacanjaComponent}
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
    OdabirPlacanjaComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    RouterModule.forRoot(Routes),
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [{ provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true }, ZuulPath],
  bootstrap: [AppComponent]
})
export class AppModule { }
