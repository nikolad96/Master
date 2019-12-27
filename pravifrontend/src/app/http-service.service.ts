import { Injectable } from '@angular/core';
import { HttpClient} from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {

  constructor(private http: HttpClient) { }

  postStart(url){
    return this.http.post(url, "");
  }

  postMonitor(url, id){
    return this.http.post(url, id);
  }

  get(url, responseType){
    return this.http.get(url, responseType);
  }

}
