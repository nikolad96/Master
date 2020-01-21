import { Injectable } from '@angular/core';
import { ZuulPath } from 'src/app/ZuulPath';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CasopisService {

  constructor(private http: HttpClient, private zuul: ZuulPath) { }

  startProcess(){
    return this.http.get(this.zuul.path + 'casopis/get') as Observable<any>
  }

  postCasopisData(casopis, taskId) {
    return this.http.post(this.zuul.path + "casopis/postCasopisData/".concat(taskId), casopis) as Observable<any>;
  }

  getUredniciRecenzenti(procesId){
    return this.http.get(this.zuul.path + "casopis/getUredniciRecenzenti/".concat(procesId)) as Observable<any>;
  }

  postUredniciRecenzentiData(uredniciRecenzenti, taskId){
    return this.http.post(this.zuul.path + "casopis/postUredniciRecenzentiData/".concat(taskId), uredniciRecenzenti) as Observable<any>;
  }

  getTasks(){
    return this.http.get(this.zuul.path + 'casopis/getTasks') as Observable<any>
  }

  postAdminCasopisData(adminCasopisData, taskId) {
    return this.http.post(this.zuul.path + "casopis/postAdminCasopisData/".concat(taskId), adminCasopisData) as Observable<any>;
  }

  getUrednikTasks(){
    return this.http.get(this.zuul.path + 'casopis/getUrednikTasks') as Observable<any>
  }

  postIspravkaData(ispravkaData, taskId) {
    return this.http.post(this.zuul.path + "casopis/postIspravkaData/".concat(taskId), ispravkaData) as Observable<any>;
  }

}
