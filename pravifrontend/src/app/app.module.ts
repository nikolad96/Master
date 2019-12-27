import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NCComponent } from './nc/nc.component';
import { KPComponent } from './kp/kp.component';

@NgModule({
  declarations: [
    AppComponent,
    NCComponent,
    KPComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
