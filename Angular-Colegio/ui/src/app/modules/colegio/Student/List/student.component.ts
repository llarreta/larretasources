//Angular components
import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';

//Models
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { Division } from '../../Models/Division.model';
import { Level } from '../../Models/Level.model';
import { Year } from '../../Models/Year.model';

//Components student
import { StudentCreateComponent } from '../Create/student.create.component';

//Commons
import { I18n } from '../../../../i18n/i18n';
import { Logger } from '../../../../logger/logger';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { InputModel } from '../../Commons/Input/input.model.component';

//ngPrime
import { TableModel } from '../../Commons/Table/table.model.component';
import { ColumnModel } from '../../Commons/Table/column.model.component';
import { ButtonTableModel } from '../../Commons/Table/button.table.model.component';
import { ActionButtonTable } from '../../Commons/Table/actionButtonTable.model.component';
import {Header} from 'primeng/primeng';
import {Footer} from 'primeng/primeng';
import { SelectItem } from 'primeng/primeng';

//Services
import { StudentService } from '../../services/student.service';
import { LevelService } from '../../services/level.service';
import { YearService } from '../../services/year.service';
import { DivisionService } from '../../services/division.service';
import { CourseService } from '../../services/course.service';

@Component({
  selector: 'school-students',
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
  
  //Filters
  filterName: string;
  filterLevel: Level;
  filterLevelsOptions: Array<SelectItem>;
  filterYear: Year;
  filterYearOptions: Array<SelectItem>;
  filterDivision: Division;
  filterDivisionOptions: Array<SelectItem>;
  
  //Errors
  showMessageError: boolean;
  showMessageErrorService: boolean; 
  messageErrorService: string;

  private language: string;

  constructor(private studentService: StudentService, private courseService: CourseService, 
              private levelService: LevelService, private divisionService: DivisionService,
              private yearService: YearService) {}

  ngOnInit() {
    this.language = "ES";
    this.selectedStudent = new Student();
    this.students = new Array<Student>();
    this.inListStudent = true;
    this.inCreateStudent = false;
    this.inUpdateStudent = false;
    this.moreStudents = true;
    this.loading = false;
    this.loadInitData();
  }

  private loadInitData(){
    this.divisionService.loadDivisions()
       .subscribe(
        data => this.loadDivisionsOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion divisionService...')
    );
    this.levelService.loadLevels()
       .subscribe(
        data => this.loadLevelsOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion levelService...')
    );
    this.yearService.loadYears()
       .subscribe(
        data => this.loadYearsOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion yearService...')
    );
    this.studentService.loadStudents()
       .subscribe(
        data => this.loadStudentsOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion studentService...')
    );
  }

  loadDivisionsOK(data){
    this.filterDivisionOptions = new Array<SelectItem>();
    this.filterDivisionOptions.push({label:"Seleccionar Division", value:null});
    for(let divisionJSON of data.body.result){
      let division: Division = new Division();
      Object.assign(division, divisionJSON);
      this.filterDivisionOptions.push({label:division.description, value:division});
    }
  }

  loadLevelsOK(data){
    this.filterLevelsOptions = new Array<SelectItem>();
    this.filterLevelsOptions.push({label:"Seleccionar Nivel", value:null});
    for(let levelJSON of data.body.result){
      let level: Level = new Level();
      Object.assign(level, levelJSON);
      this.filterLevelsOptions.push({label:level.description, value:level});
    }
  }

  loadYearsOK(data){
    this.filterYearOptions = new Array<SelectItem>();
    this.filterYearOptions.push({label:"Seleccionar Año", value:null});
    for(let yearJSON of data.body.result){
      let year: Year = new Year();
      Object.assign(year, yearJSON);
      this.filterYearOptions.push({label:year.description, value:year});
    }
  }

  loadStudentsOK(data){
    Logger.debug("Estudiantes recibidos.. " + JSON.stringify(data));
    this.students = new Array<Student>();
    for(let studentJSON of data.body.result){
      let student: Student = new Student();
      Object.assign(student, studentJSON);
      this.students.push(student);
    }
    Logger.debug("Estudiantes cargados.. " + JSON.stringify(this.students));
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
    this.loadInitData();
  }

  loadStudent(student: Student){
    this.selectedStudent = student;
    this.inUpdateStudent = true;
    this.inListStudent = false;
    this.inCreateStudent = false;
  }

  loadErrorMessageService(error){
    Logger.warn("Ocurrio un error al crear un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }

}