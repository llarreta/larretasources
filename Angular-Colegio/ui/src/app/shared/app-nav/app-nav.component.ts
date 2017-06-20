import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-nav',
  templateUrl: './src/app/shared/app-nav/app-nav.component.html',
  styleUrls: ['./src/app/shared/app-nav/app-nav.component.scss'],
})
export class AppNavComponent {

  menuActive: boolean = false;

  constructor(private router: Router) { }

  public openCloseMenu(){
    this.menuActive = !this.menuActive;
  }

  public goStudents(){
    this.router.navigate(['./colegio/students']);
    this.menuActive = false;
  }

  public goHome(){
    this.router.navigate(['./colegio/home']);
    this.menuActive = false;
  }

  public goCourses(){
    console.debug("router: " + this.router.url);
    this.router.navigate(['./colegio/courses']);
    this.menuActive = false;
  }

  public goPaymentPlans(){
    this.router.navigate(['./colegio/paymentPlan']);
    this.menuActive = false;
  }

  public goPaymentRecord(){
    this.router.navigate(['./colegio/paymentRecord']);
    this.menuActive = false;
  }

}