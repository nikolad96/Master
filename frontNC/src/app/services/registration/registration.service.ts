import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ZuulPath } from 'src/app/ZuulPath';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private http: HttpClient, private zuul: ZuulPath) { }

  startProcess(){
    return this.http.get(this.zuul.path + 'registration/get') as Observable<any>
  }

  postRegistrationData(user, taskId) {
    return this.http.post(this.zuul.path + "registration/postRegistrationData/".concat(taskId), user) as Observable<any>;
  }

  activateUser(id_user, id_process) {
    return this.http.post(this.zuul.path + "registration/activateUser/".concat(id_process), id_user) as Observable<any>;
  }

  getTasks(){
    return this.http.get(this.zuul.path + 'registration/getTasks') as Observable<any>
  }

  recenzentPotvrda(taskId){
    return this.http.post(this.zuul.path + 'registration/recenzentPotvrda', taskId) as Observable<any>
  }

  recenzentOdbijanje(taskId){
    return this.http.post(this.zuul.path + 'registration/recenzentOdbijanje', taskId) as Observable<any>
  }

}
