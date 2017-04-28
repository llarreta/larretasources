import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { StudentCreateComponent } from '../Create/student.create.component';
import { StudentUpdateComponent } from '../Update/student.update.component';
import { TableModel } from '../../Commons/Table/table.model.component';
import { ColumnModel } from '../../Commons/Table/column.model.component';
import { I18n } from '../../../../i18n/i18n';
import { Logger } from '../../../../logger/logger';
import { ButtonTableModel } from '../../Commons/Table/button.table.model.component';
import { ActionButtonTable } from '../../Commons/Table/actionButtonTable.model.component';
import {Header} from 'primeng/primeng';
import {Footer} from 'primeng/primeng';

@Component({
  selector: 'colegio-alumnos',
  templateUrl: './src/app/modules/colegio/Student/List/student.component.html'
})
export class StudentComponent implements OnInit{

  students: Array<Student>;
  selectedStudent: Student;
  inCreateStudent: boolean;
  inUpdateStudent: boolean;
  inListStudent: boolean;
  loading: boolean;
  moreStudents: boolean;

  private language: string;



  constructor() {}

  ngOnInit() {

    this.language = "ES";
    this.selectedStudent = new Student();
    this.students = new Array<Student>();
    this.cargarStudentTest();
    this.inListStudent = true;
    this.inCreateStudent = false;
    this.inUpdateStudent = false;
    this.moreStudents = true;
    this.loading = false;
    //this.cargarStudents();

  }



    protected fetchNextChunk(skip: number, limit: number): Promise<Student[]> {
        return new Promise((resolve, reject) => {
            new Array<Student>();
        });
    }

  
  cargarStudentTest(){
    let course: Course = new Course();
    course.division = "Mercantil";
    course.level = "Secundario";
    course.year = "3";

    let student: Student = new Student();
    student.id = 1;
    student.name = "Jorge";
    student.email = "jorge@email.com";
    student.documentNumber = 38287965;
    student.documentType = "DNI";
    student.surname = "Ejemplo";
    student.course = course;

    let student2: Student = new Student();
    student2.id = 2;
    student2.name = "Leo";
    student2.email = "leo@email.com";
    student2.documentNumber = 38287123;
    student2.documentType = "DNI";
    student2.surname = "Ejemplo2";
    student2.course = course;

    let student3: Student = new Student();
    student3.id = 3;
    student3.name = "Cacho";
    student3.email = "cacho@email.com";
    student3.documentNumber = 38284567;
    student3.documentType = "DNI";
    student3.surname = "Ejemplo3";
    student3.course = course;

    let student4: Student = new Student();
    student4.id = 4;
    student4.name = "Cacho4";
    student4.email = "cacho4@email.com";
    student4.documentNumber = 33284567;
    student4.documentType = "DNI";
    student4.surname = "Ejemplo4";
    student4.course = course;

    let student5: Student = new Student();
    student5.id = 5;
    student5.name = "Cacho5";
    student5.email = "cacho5@email.com";
    student5.documentNumber = 35284567;
    student5.documentType = "DNI";
    student5.surname = "Ejemplo5";
    student5.course = course;

    let student6: Student = new Student();
    student6.id = 6;
    student6.name = "Cacho6";
    student6.email = "cacho6@email.com";
    student6.documentNumber = 36284567;
    student6.documentType = "DNI";
    student6.surname = "Ejemplo6";
    student6.course = course;

    let student7: Student = new Student();
    student7.id = 7;
    student7.name = "Cacho7";
    student7.email = "cacho7@email.com";
    student7.documentNumber = 37284567;
    student7.documentType = "DNI";
    student7.surname = "Ejemplo7";
    student7.course = course;

    this.students = [];
    this.students.push(student);
    this.students.push(student2);
    this.students.push(student3);
    this.students.push(student4);
    this.students.push(student5);
    this.students.push(student6);
    this.students.push(student7);
    this.students.push(student7);
    this.students.push(student7);
    this.students.push(student7);
    this.students.push(student7);
    this.students.push(student7);
  }

  goListCreate(goList: boolean) {
    this.inUpdateStudent = false;
    this.inCreateStudent = !goList;
    this.inListStudent = goList;
  }

  goListUpdate(goList: boolean){
    this.inCreateStudent = false;
    this.inUpdateStudent = !goList;
    this.inListStudent = goList;
  }

  loadData(event) {
      this.cargarStudentTest();
  }

}