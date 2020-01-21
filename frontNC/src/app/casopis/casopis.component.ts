import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { CasopisService } from '../services/casopis/casopis.service';

@Component({
  selector: 'app-casopis',
  templateUrl: './casopis.component.html',
  styleUrls: ['./casopis.component.css']
})
export class CasopisComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValuesNaplacivanje = [];
  private enumValuesNaucneOblasti = [];
  private casopisForm: FormGroup;
  private naziv: FormControl;
  private ISSN_broj: FormControl;
  private naplata_clanarine: FormControl;
  private naucna_oblast: FormControl;

  constructor(private casopisService: CasopisService) {

    casopisService.startProcess().subscribe(
      res => {
        console.log(res);
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          
          if( field.type.name=='enum' && field.id=='naplata_clanarine'){
            this.enumValuesNaplacivanje = Object.keys(field.type.values);
          }else if( field.type.name=='enum' && field.id=='naucna_oblast'){
            this.enumValuesNaucneOblasti = Object.keys(field.type.values);
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
    this.naziv = new FormControl('', Validators.required);
    this.ISSN_broj = new FormControl('', [Validators.required, Validators.minLength(9), Validators.maxLength(9), Validators.pattern('[0-9]{4}-[0-9]{4}')]);
    this.naplata_clanarine = new FormControl('', Validators.required);
    this.naucna_oblast = new FormControl('', Validators.required);
    
  }
  createForm(){
    this.casopisForm = new FormGroup({
      naziv: this.naziv,
      ISSN_broj: this.ISSN_broj,
      naplata_clanarine: this.naplata_clanarine,
      naucna_oblast: this.naucna_oblast
    });
    
  }

  onSubmitCasopisForm(value, form){

    let o = new Array();
    for (var property in value) {
      if(property != 'naucna_oblast'){
        console.log(property, ': ', value[property]);
        o.push({fieldId : property, fieldValue : value[property]});
      }else{
        for(var pom of value[property]){
          o.push({fieldId : property, fieldValue : pom});
        }
      }
    }

    console.log(o);

    this.casopisService.postCasopisData(o, this.formFieldsDto.taskId).subscribe(
      res => {
        console.log("res", res);
        window.location.href="urednici-recenzenti/" + this.processInstance;
    
      },
      err => {
        console.log("Error occured");
      }
    );

  }

  nazad(){
    window.location.href='';
  }

}
