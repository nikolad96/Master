import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaypalServiceService {

  constructor(private http: HttpClient) { }
  postCustomerData(dto){
    return this.http.post('https://localhost:8087/paypal/newSellerData', dto) as Observable<any>
  }
  createPlan(dto){
    return this.http.post('https://localhost:8087/paypal/createPlan', dto) as Observable<any>
  }
}
