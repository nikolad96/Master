import { Component, OnInit } from '@angular/core';
import { CasopisService } from '../services/casopis/casopis.service';
import { ActivatedRoute } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';

@Component({
  selector: 'app-urednici-recenzenti',
  templateUrl: './urednici-recenzenti.component.html',
  styleUrls: ['./urednici-recenzenti.component.css']
})
export class UredniciRecenzentiComponent implements OnInit {

  private id_process;
  private formFieldsDto = null;
  private formFields = [];
  private enumUrednici = [];
  private enumRecenzenti = [];
  private uredniciRecenzentiForm: FormGroup;
  private urednici: FormControl;
  private recenzenti: FormControl;
  private errorRecenzenti = false;

  constructor(private casopisService: CasopisService, private route: ActivatedRoute) {

    this.route.params.subscribe( params => {
      this.id_process = params.id_process;
    });

    casopisService.getUredniciRecenzenti(this.id_process).subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum' && field.id=='urednici'){
            this.enumUrednici = Object.keys(field.type.values);
          }else if( field.type.name=='enum' && field.id=='recenzenti'){
            this.enumRecenzenti = Object.keys(field.type.values);
          }
        });

      },
      err => {
        console.log("Error occured");
      }
    );
   }

  ngOnInit() {
    this.createFormControls();
    this.createForm();
  }

  createFormControls(){
    this.urednici = new FormControl('');
    this.recenzenti = new FormControl('', Validators.required);
    
  }
  createForm(){
    this.uredniciRecenzentiForm = new FormGroup({
      urednici: this.urednici,
      recenzenti: this.recenzenti
    });
    
  }

  onSubmituredniciRecenzentiForm(value, form){

    this.errorRecenzenti = false;

    let o = new Array();
    for (var property in value) {

      if(property == 'recenzenti' && value[property].length<2){
        console.log('odabrano manje od 2 recenzenta');
        this.errorRecenzenti = true;
      }
    
    }

    if(this.errorRecenzenti == false){

      for(var property in value){
        for(var pom of value[property]){
          console.log(property, ': ', pom);
          o.push({fieldId : property, fieldValue : pom});
        }
      }

      console.log(o);

      this.casopisService.postUredniciRecenzentiData(o, this.formFieldsDto.taskId).subscribe(
        res => {
          console.log("casopisId");
          console.log(res);
          // alert('Casopis uspesno sacuvan.')
          // window.location.href="";
          window.location.href="casopis-placanje/" + res;
      
        },
        err => {
          console.log("Error occured");
        }
      );

    }

    

  }

  nazad(){
    window.location.href='';
  }

}
