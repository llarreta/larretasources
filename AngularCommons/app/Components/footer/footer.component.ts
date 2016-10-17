import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  moduleId: module.id,
  selector: 'footer-common',
  templateUrl: 'footer.html',
  styleUrls: ['footer.css']
})
export class FooterComponent implements OnInit {


  constructor(
    private router: Router) {
  }
  
  ngOnInit(){
    }

}
