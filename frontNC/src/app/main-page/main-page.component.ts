import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../services/registration/registration.service';
import { AuthService } from '../services/auth/auth.service';

@Component({
  selector: 'app-main-page',
  templateUrl: './main-page.component.html',
  styleUrls: ['./main-page.component.css']
})
export class MainPageComponent implements OnInit {

  isAdmin: boolean = false;
  isLoggedIn: boolean = false;
  isUrednik: boolean = false;
  isRecenzent: boolean = false;

  constructor(private authService: AuthService) { }

  ngOnInit() {
    let user = this.authService.getLoggedUser();
    if(user != null){
      this.isLoggedIn = true; 

      this.authService.getUser().subscribe(
        (user: any) => {
          if(user.role === 'ADMIN'){
            console.log("ADMIN");
            this.isAdmin = true;
          }else if(user.role  === 'UREDNIK'){
            console.log('UREDNIK');
            this.isUrednik = true;
          }else if(user.role  === 'RECENZENT'){
            console.log('RECENZENT');
            this.isRecenzent = true;
          }
        }
      )
    }

    
  }

  logOut(){
    this.authService.logout();
    this.isLoggedIn = false;
    this.isAdmin = false;
    this.isUrednik = false;
    this.isRecenzent = false;
  }


}
