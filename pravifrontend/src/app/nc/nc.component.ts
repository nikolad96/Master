import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nc',
  templateUrl: './nc.component.html',
  styleUrls: ['./nc.component.css']
})
export class NCComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  showSelectBox(option){
    if(option == "1"){

    }
  }

  nextPage() {
    window.location.href = 'KP-page';
  }

}
