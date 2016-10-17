import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'body-common',
  templateUrl: 'body.html',
  styleUrls: ['body.css']
})
export class BodyComponent implements OnInit {


  constructor(
    private router: Router) {
  }
  
  ngOnInit(){
    }

}
