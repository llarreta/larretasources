import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { StudentCreateComponent } from '../Create/student.create.component';
import { StudentUpdateComponent } from '../Update/student.update.component';
import { TableModel } from '../../Commons/Table/table.model.component';
import { ColumnModel } from '../../Commons/Table/column.model.component';
import { I18n } from '../../../../i18n/i18n';

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
  table: TableModel;

  private language: string;

  constructor() {}

  ngOnInit() {

    this.language = "ES";
    this.selectedStudent = new Student();
    this.cargarStudentTest();
    this.inListStudent = true;
    this.inCreateStudent = false;
    this.inUpdateStudent = false;
    this.loadTable();
    //this.cargarStudents();

  }

  loadTable(){
    
    this.table = new TableModel();
    this.table.paginator = 5;
    this.table.values = JSON.stringify(this.students);
    this.table.globalFilter = true;
    this.table.columns = new Array<ColumnModel>();

    let columnName: ColumnModel = new ColumnModel();
    columnName.header = I18n.getMessage("commonsName", this.language);
    columnName.key = "name";

    let columnSurname: ColumnModel = new ColumnModel();
    columnSurname.header = I18n.getMessage("commonsSurname", this.language);
    columnSurname.key = "surname";

    let columnEmail: ColumnModel = new ColumnModel();
    columnEmail.header = I18n.getMessage("commonsEmail", this.language);
    columnEmail.key = "email";

    let columnDocumentType: ColumnModel = new ColumnModel();
    columnDocumentType.header = I18n.getMessage("commonsDocumentType", this.language);
    columnDocumentType.key = "documentType";

    let columnDocumentNumber: ColumnModel = new ColumnModel();
    columnDocumentNumber.header = I18n.getMessage("commonsDocumentNumber", this.language);
    columnDocumentNumber.key = "documentNumber";

    let columnLevel: ColumnModel = new ColumnModel();
    columnLevel.header = I18n.getMessage("commonsLevel", this.language);
    columnLevel.key = "course.level";

    let columnYear: ColumnModel = new ColumnModel();
    columnYear.header = I18n.getMessage("commonsYear", this.language);
    columnYear.key = "course.year";

    let columnDivision: ColumnModel = new ColumnModel();
    columnDivision.header = I18n.getMessage("commonsDivision", this.language);
    columnDivision.key = "course.division";

    this.table.columns.push(columnName);
    this.table.columns.push(columnSurname);
    this.table.columns.push(columnEmail);
    this.table.columns.push(columnDocumentType);
    this.table.columns.push(columnDocumentNumber);
    this.table.columns.push(columnLevel);
    this.table.columns.push(columnYear);
    this.table.columns.push(columnDivision);

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

}