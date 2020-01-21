import { Component, OnInit } from '@angular/core';
import { CasopisService } from '../services/casopis/casopis.service';

@Component({
  selector: 'app-ispravka',
  templateUrl: './ispravka.component.html',
  styleUrls: ['./ispravka.component.css']
})
export class IspravkaComponent implements OnInit {

  private tasks = [];
  private controls = [];
  private formFields = [];
  private enumValuesNaplataClanarine = [];
  private enumValuesStareNaucneOblasti = [];
  private enumValuesNaucneOblasti = [];
  private enumValuesStariUrednici = [];
  private enumValuesUrednici = [];
  private enumValuesStariRecenzenti = [];
  private enumValuesRecenzenti = [];
  private errorRecenzenti = false;
  private error = false;

  constructor(private casopisService: CasopisService) { }

  ngOnInit() {

    let x = this.casopisService.getUrednikTasks();

    x.subscribe(
      res => {
        console.log('res:', res);
        this.tasks = res;
        this.formFields = res.formFields;
        this.tasks.forEach( (task) => {
          task.formFields.forEach( (field) => {
            if(field.type.name=='enum' && field.id=='naplata_clanarine'){
              this.enumValuesNaplataClanarine = Object.keys(field.type.values);
            }else if(field.type.name=='enum' && field.id=='stare_naucne_oblasti'){
              this.enumValuesStareNaucneOblasti = Object.keys(field.type.values);
            }else if(field.type.name=='enum' && field.id=='naucna_oblast'){
              this.enumValuesNaucneOblasti = Object.keys(field.type.values);
            }else if(field.type.name=='enum' && field.id=='stari_urednici'){
              this.enumValuesStariUrednici = Object.keys(field.type.values);
            }else if(field.type.name=='enum' && field.id=='urednici_novi'){
              this.enumValuesUrednici = Object.keys(field.type.values);
            }else if(field.type.name=='enum' && field.id=='stari_recenzenti'){
              this.enumValuesStariRecenzenti = Object.keys(field.type.values);
            }else if(field.type.name=='enum' && field.id=='recenzenti_novi'){
              this.enumValuesRecenzenti = Object.keys(field.type.values);
            }
          });
          
        });
      },
      err => {
        console.log("Error occured");
      }
    );
  }

  onSubmitForm(value, form, taskId){

    console.log('taskId: ' + taskId);
    this.errorRecenzenti = false;
    this.error = false;

    let dto = Array();
    this.controls = form.controls;

    for (var control in this.controls) {

      if(control == 'recenzenti_novi' && this.controls[control].value.length<2){
        console.log('odabrano manje od 2 recenzenta');
        this.errorRecenzenti = true;
      }
      if((control == 'naziv' && this.controls[control].value=='') || (control == 'ISSN_broj' && this.controls[control].value=='') || (control == 'naplata_clanarine' && this.controls[control].value=='') || (control == 'naucna_oblast' && this.controls[control].value=='') || (control == 'recenzenti_novi' && this.controls[control].value=='')){
        console.log('nije popunjeno');
        this.error = true;
      }
    
    }

    if(this.errorRecenzenti == false && this.error == false){
     
      for(var control in this.controls){
        if(control != 'naucna_oblast' && control != 'urednici_novi' && control != 'recenzenti_novi'){
          if(control != 'stare_naucne_oblasti' && control != 'stari_urednici' && control != 'stari_recenzenti'){
            dto.push({fieldId: control, fieldValue: this.controls[control].value});
          }
        }else{
          for(var pom of this.controls[control].value){
            dto.push({fieldId : control, fieldValue : pom});
          }
        }
      }
      console.log(dto);

      this.casopisService.postIspravkaData(dto, taskId).subscribe(
        res => {
          console.log("res", res);
          alert('Uspesno ste poslali ispravljene podatke o casopisu.')
          window.location.href="ispravka";
      
        },
        err => {
          console.log("Error occured");
        }
      );
    }

  }

  nazad(){
    window.location.href = "";
  }

}
