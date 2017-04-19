import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
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
    this.table.emptyMessage = "No se encontraron alumnos cargados.";
    this.table.values = new Array<string>();
    for(let student of this.students){
      this.table.values.push(JSON.stringify(student));
    }
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

    let columnButtons: ColumnModel = new ColumnModel();
    columnButtons.header = "";
    columnButtons.columnButton = true;
    columnButtons.classStyle = "table-buttons-default";

    let editButton: ButtonTableModel = new ButtonTableModel();
    editButton.action = "editStudent";
    editButton.iconAwesome = "fa-pencil";
    editButton.iconAwesomeStyleClass = "edit-button-table-student-icon";
    editButton.styleClass = "edit-button-table-student";

    let deleteButton: ButtonTableModel = new ButtonTableModel();
    deleteButton.action = "deleteStudent";
    deleteButton.iconAwesome = "fa-trash";
    deleteButton.iconAwesomeStyleClass = "delete-button-table-student-icon";
    deleteButton.styleClass = "delete-button-table-student";

    columnButtons.buttons = new Array<ButtonTableModel>();
    columnButtons.buttons.push(editButton);
    columnButtons.buttons.push(deleteButton);

    this.table.columns.push(columnName);
    this.table.columns.push(columnSurname);
    this.table.columns.push(columnEmail);
    this.table.columns.push(columnDocumentType);
    this.table.columns.push(columnDocumentNumber);
    this.table.columns.push(columnLevel);
    this.table.columns.push(columnYear);
    this.table.columns.push(columnDivision);
    this.table.columns.push(columnButtons);

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

    this.students = new Array<Student>();
    this.students.push(student);
    this.students.push(student2);
    this.students.push(student3);
    this.students.push(student4);
    this.students.push(student5);
    this.students.push(student6);
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

  actionCatch(action: ActionButtonTable){
    Logger.debug("Lista de estudiantes accion capturada...");
    Logger.debug("action: " + action.action);
    Logger.debug("value: " + action.value);
  }

}