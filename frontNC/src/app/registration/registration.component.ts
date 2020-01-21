import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../services/registration/registration.service'
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];
  private registrationForm: FormGroup;
  private ime: FormControl;
  private prezime: FormControl;
  private grad: FormControl;
  private drzava: FormControl;
  private titula: FormControl;
  private email: FormControl;
  private username: FormControl;
  private password: FormControl;
  private recenzent: FormControl;
  private naucna_oblast;
  private show;
  private postojiMail;
  private postojiUsername;


  constructor(private registrationService: RegistrationService) { 

    registrationService.startProcess().subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });

        this.show = 1;
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  ngOnInit() {
    this.createFormControls();
    this.createForm();

    this.postojiMail = false;
    this.postojiUsername = false;
  }

  createFormControls(){
    this.ime = new FormControl('', Validators.required);
    this.prezime = new FormControl('', Validators.required);
    this.grad = new FormControl('', Validators.required);
    this.drzava = new FormControl('', Validators.required);
    this.titula = new FormControl('');
    this.email = new FormControl('', Validators.required);
    this.username = new FormControl('', Validators.required);
    this.password = new FormControl('', Validators.required);
    this.recenzent = new FormControl('');
    this.naucna_oblast = new FormControl('', Validators.required);
    
  }
  createForm(){
    this.registrationForm = new FormGroup({
      ime: this.ime,
      prezime: this.prezime,
      grad: this.grad,
      drzava: this.drzava,
      titula: this.titula,
      email: this.email,
      username: this.username,
      password: this.password,
      recenzent: this.recenzent,
      naucna_oblast: this.naucna_oblast
    });
    
  }

  onSubmitRegistrationForm(value, form){

    this.postojiUsername = false;
    this.postojiMail = false;

    let o = new Array();
    for (var property in value) {
      if(property != 'naucna_oblast'){
        if((value[property] == '')){
          if(property == 'recenzent')
            value[property] = false;
          else
            value[property] = null;
        }
        o.push({fieldId : property, fieldValue : value[property]});
      }else{
        for(var pom of value[property]){
          o.push({fieldId : property, fieldValue : pom});
        }
      }
    }

    console.log(o);

    

    this.registrationService.postRegistrationData(o, this.formFieldsDto.taskId).subscribe(
      res => {
        console.log("res", res);
        if(res[0].fieldId == "ok"){
          this.show = 2;
        }else{
          if(res[0].fieldValue == "true"){
            //mail vec postoji
            this.postojiMail = true;

          }
          if(res[1].fieldValue == "true"){
            //username vec postoji
            this.postojiUsername = true;

          }
        }
    
      },
      err => {
        console.log("Error occured");
      }
    );

  }

  nazad(){
    window.location.href="";
  }

}
