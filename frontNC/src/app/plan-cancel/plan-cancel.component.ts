import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-plan-cancel',
  templateUrl: './plan-cancel.component.html',
  styleUrls: ['./plan-cancel.component.css']
})
export class PlanCancelComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  redirect(){
    window.location.href ='/';
  }
}
