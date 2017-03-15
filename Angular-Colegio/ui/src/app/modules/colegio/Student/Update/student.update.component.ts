import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { Student } from '../../Models/Student.model';

@Component({
  selector: 'colegio-alumnos-update',
  templateUrl: './src/app/modules/colegio/Student/Update/student.update.component.html'
})
export class StudentUpdateComponent implements OnInit{

  @Input() 
  student: Student;

  constructor() {}

  ngOnInit() {

  }

}