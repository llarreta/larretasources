import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';

@Component({
  selector: 'colegio-alumnos-update',
  templateUrl: './src/app/modules/colegio/Student/Update/student.component.html'
})
export class StudentComponent implements OnInit{

  students: Array<Student>;
  selectedStudent: Student;
  inCreateStudent: boolean;
  inUpdateStudent: boolean;
  inListStudent: boolean;

  constructor() {}

  ngOnInit() {

    this.cargarStudentTest();
    this.inListStudent = true;
    this.inCreateStudent = false;
    this.inUpdateStudent = false;
    //this.cargarStudents();

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

    this.students = new Array<Student>();
    this.students.push(student);
    this.students.push(student2);
    this.students.push(student3);
  }

}