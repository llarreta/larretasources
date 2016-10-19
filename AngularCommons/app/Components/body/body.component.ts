import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'body-common',
  templateUrl: 'body.html',
  styleUrls: ['body.css']
})
export class BodyComponent implements OnInit {

   private isMenuVisible:boolean;

  constructor(
    private router: Router) {
  }
  
  ngOnInit(){
    this.isMenuVisible=true;
  }

  openMenuLeft(event){
    this.isMenuVisible = !this.isMenuVisible; 
  }

}
