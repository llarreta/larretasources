import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'header-common',
  templateUrl: 'header.html',
  styleUrls: ['header.css']
})
export class HeaderComponent implements OnInit {


  constructor(
    private router: Router) {
  }
  
  ngOnInit(){
    }

}
