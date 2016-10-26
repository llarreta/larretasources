import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

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
