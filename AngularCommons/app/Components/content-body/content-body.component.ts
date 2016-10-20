import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'content-body',
  templateUrl: 'content-body.html',
  styleUrls: ['content-body.css']
})
export class ContentBodyComponent implements OnInit {


  constructor(
    private router: Router) {
  }
  
  ngOnInit(){
  }

}
