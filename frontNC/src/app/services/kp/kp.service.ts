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
}
