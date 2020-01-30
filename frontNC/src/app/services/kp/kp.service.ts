import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ZuulPath } from 'src/app/ZuulPath';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class KpService {

  constructor(private http: HttpClient, private zuul: ZuulPath) { }

  getCasopisi(){
    return this.http.get(this.zuul.path + 'KP/allMagazines') as Observable<any>
  }

  provera(radId, casopisId){
    return this.http.get(this.zuul.path + 'KP/checkPaid/'.concat(radId) + '/'.concat(casopisId)) as Observable<any>
  }

  getNaciniPlacanjaCasopis(casopisId){
    return this.http.get(this.zuul.path + 'KP/getPaymentMethods/'.concat(casopisId)) as Observable<any>
  }

  placanjeBanka(radId, casopisId){
    return this.http.get(this.zuul.path + 'KP/paymentBank/'.concat(radId) + '/'.concat(casopisId)) as Observable<any>
  }

  getNaciniPlacanja(){
    return this.http.get(this.zuul.path + 'KP/getAllPaymentMethods') as Observable<any>
  }

  noviCasopis(id_casopis, id_nacin_placanja){
    return this.http.get(this.zuul.path + 'KP/noviCasopis/'.concat(id_casopis) + '/'.concat(id_nacin_placanja)) as Observable<any>
  }

  placanjeBitcoin(radId, casopisId, userId){
  return this.http.post(this.zuul.path + 'KP/paymentBitcoin'.concat(radId) + '/'.concat(casopisId) + '/'.concat(userId), '') as Observable<any>

}

}
