import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { ZuulPath } from 'src/app/ZuulPath';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedUser: any;

  constructor(private http: HttpClient, private zuul: ZuulPath, private router: Router) { }

  login(user){
    return this.http.post(this.zuul.path + 'auth/login', user)
    .pipe(
        map(user => {
            this.loggedUser = user;
            if(user){
                localStorage.setItem('currentUser', JSON.stringify(user));
            }
            return user;
        })
    )
  }

  logout(){
      return this.http.post(this.zuul.path + 'auth/logout', null).subscribe(
          success => {
              localStorage.removeItem('currentUser');
              this.router.navigate(['']);
        }, error => alert('Error while trying to logout.')
      )
  }

  getLoggedUser() {
      return localStorage.getItem('currentUser');
  }

  getUser(){
    return this.http.get(this.zuul.path + 'auth/getUser');  
  }
  
}
