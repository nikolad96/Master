import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ZuulPath } from 'src/app/ZuulPath';
import { Observable } from 'rxjs';
import { BankPath } from 'src/app/BankPath';
import { CardRequestDTO } from 'src/app/model/CardRequestDTO';

@Injectable({
  providedIn: 'root'
})
export class KpService {

  constructor(private http: HttpClient, private zuul: ZuulPath, private bankPath: BankPath) { }

  getCasopisi(){
    return this.http.get(this.zuul.path + 'KP/allMagazines') as Observable<any>
  }

  provera(radId, casopisId){
    return this.http.get(this.zuul.path + 'KP/checkPaid/'.concat(radId) + '/'.concat(casopisId)) as Observable<any>
  }

  getNaciniPlacanja(casopisId){
    return this.http.get(this.zuul.path + 'KP/getPaymentMethods/'.concat(casopisId)) as Observable<any>
  }

  placanjeBanka(radId, casopisId){
    return this.http.get(this.zuul.path + 'KP/paymentBank/'.concat(radId) + '/'.concat(casopisId)) as Observable<any>
  }

  checkAccount(cardRequestDTO: CardRequestDTO){
    return this.http.post(this.bankPath.path + 'bank/checkAccountAcquirer', cardRequestDTO) as Observable<any>
  }

  executePayment(cardRequestDTO: CardRequestDTO){
    return this.http.post(this.bankPath.path + 'bank/executePayment', cardRequestDTO) as Observable<any>
  }
}
