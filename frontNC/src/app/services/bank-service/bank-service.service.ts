import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BankServicePath } from 'src/app/BankServicePath';
import { CustomerDTO } from 'src/app/model/CustomerDTO';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BankServiceService {

  constructor(private http: HttpClient) { }

  postCustomerData(customerDTO: CustomerDTO){
    return this.http.post('https://localhost:8085/bankservice/postCustomerData', customerDTO) as Observable<any>
  }
}
