import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { ObligationStatus } from '../../Models/ObligationStatus.model';
import { Responsible } from '../../Models/Responsible.model';

@Component({
  selector: 'colegio-alumnos-create',
  templateUrl: './src/app/modules/colegio/Student/Create/student.create.component.html'
})
export class StudentCreateComponent implements OnInit{

  selectedStudent: Student;
  @Output()
  goList : EventEmitter<boolean>;
  paymentPlans: Array<PaymentPlan>;
  obligationsStatus: Array<ObligationStatus>;
  responsibles: Array<Responsible>;

  constructor() {}

  ngOnInit() {
    this.goList = new EventEmitter<boolean>();
    //Aca deberia cargar con los servicios todas las listas
    this.loadPaymentPlansTest();
    this.loadObligationsStatusTest();
    this.loadResponsiblesTest();
  }

  loadPaymentPlansTest(){}
  loadObligationsStatusTest(){}
  loadResponsiblesTest(){}
}