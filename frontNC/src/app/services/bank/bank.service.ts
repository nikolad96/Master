import { Injectable } from '@angular/core';
import { BankPath } from 'src/app/BankPath';
import { HttpClient } from '@angular/common/http';
import { CardRequestDTO } from 'src/app/model/CardRequestDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankService {

  constructor(private http: HttpClient, private bankPath: BankPath) { }

  checkAccount(cardRequestDTO: CardRequestDTO){
    return this.http.post(this.bankPath.path + 'bank/checkAccountAcquirer', cardRequestDTO) as Observable<any>
  }

  executePayment(cardRequestDTO: CardRequestDTO){
    return this.http.post(this.bankPath.path + 'bank/executePayment', cardRequestDTO) as Observable<any>
  }
}
