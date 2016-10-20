import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
//import {Accordion, AccordionGroup} from '@ng-bootstrap/ng-bootstrap/accordion';

@Component({
  moduleId: module.id,
  selector: 'menu-left',
  templateUrl: 'menu-left.html',
  styleUrls: ['menu-left.css']
})
export class MenuLeftComponent implements OnInit {


  constructor(
    private router: Router) {
  }
  
  ngOnInit(){
  }

}
