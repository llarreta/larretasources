import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { InputCommonsComponent } from '../Commons/Input/input.component';
import { InputModel } from '../Commons/Input/input.model.component';
import { SelectOneMenuModel } from '../Commons/SelectOneMenu/selectOneMenu.model.component';
import { OptionModel } from '../Commons/SelectOneMenu/option.model.component';
import { Router } from '@angular/router';

@Component({
  selector: 'colegio-init',
  templateUrl: './src/app/modules/colegio/Init/init.component.html',
  styleUrls: ['./src/app/modules/colegio/Init/init.component.scss']
})
export class InitComponent{

  constructor(private router: Router) { }

  public goStudents(){
    this.router.navigate(['./colegio/students']);
  }

  public goHome(){
    this.router.navigate(['./colegio/home']);
  }

  public goCourses(){
    console.debug("router: " + this.router.url);
    this.router.navigate(['./colegio/courses']);
  }

  public goPaymentPlans(){
    this.router.navigate(['./colegio/paymentPlan']);
  }

  public goPaymentRecord(){
    this.router.navigate(['./colegio/paymentRecord']);
  }

}