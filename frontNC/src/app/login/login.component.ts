import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthService } from '../services/auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm = new FormGroup({
    username: new FormControl(""),
    password: new FormControl("")
  })

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit(){

    let userDTO = {
      username: this.loginForm.value.username,
      password: this.loginForm.value.password
    }
  
    this.authService.login(userDTO).subscribe(
      (success) => {
        // alert("Uspesno ste se ulogovali.");
        this.authService.getUser().subscribe(
          (user: any) => {
            if(user.role == "ADMIN"){
              // this.router.navigate(["/recenzenti"]);
            }
          }
        )
        this.router.navigate(['']);
      }, 
      (error) => {
        alert("Podaci koje ste uneli nisu ispravni, pokusajte ponovo.");
      }
    )
  }

  nazad(){
    window.location.href="";
  }

}
