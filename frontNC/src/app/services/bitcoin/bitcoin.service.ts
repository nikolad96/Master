import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class BitcoinService {

  constructor(private http: HttpClient) { }

  postCustomerData(dto){
    return this.http.post('https://localhost:8090/bitcoin-service/newSellerData', dto) as Observable<any>
  }
}
