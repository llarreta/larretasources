//Angular components
import { Component, Input, Output, OnInit, OnDestroy, ViewChild, EventEmitter} from '@angular/core';

//Models
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { Division } from '../../Models/Division.model';
import { Level } from '../../Models/Level.model';
import { Year } from '../../Models/Year.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { DocumentType } from '../../Models/DocumentType.model';

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
import { DocumentTypeService } from '../../services/documentType.service';
import { PaymentPlanService } from '../../services/paymentPlan.service';

@Component({
  selector: 'school-students',
  templateUrl: './src/app/modules/colegio/Student/List/student.component.html'
})
export class StudentComponent implements OnInit{

  @Input()
  inPaymentRecord: boolean;
  @Output()
  goRecord = new EventEmitter();
  @Output()
  selectStudent = new EventEmitter();

  students: Array<Student>;
  documentTypes: Array<DocumentType>;
  courses: Array<Course>;

  selectedStudent: Student;
  inCreateStudent: boolean;
  inUpdateStudent: boolean;
  inListStudent: boolean;
  moreStudents: boolean;
  showPaymentRecord: boolean;
  
  //Filters
  filterName: string;
  filterLevel: Level;
  filterLevelsOptions: Array<SelectItem>;
  filterYear: Year;
  filterYearOptions: Array<SelectItem>;
  filterDivision: Division;
  filterDivisionOptions: Array<SelectItem>;
  courseSelected: Course;
  filterCourseOptions: Array<SelectItem>;

  //Paginator
  maxResult: number;
  rows: number;
  onLoadStudents: boolean;

  //Errors
  showMessageError: boolean;
  showMessageErrorService: boolean; 
  messageErrorService: string;

  es: any;
  initDate: Date;
  endDate: Date;
  
  loading: boolean;

  displayReportPopUp: string;

  private havePaymentsPlans: boolean;

  private language: string;

  constructor(private studentService: StudentService, private courseService: CourseService, 
              private levelService: LevelService, private divisionService: DivisionService,
              private yearService: YearService, private paymentPlanService: PaymentPlanService,
              private documentTypeService: DocumentTypeService) {}

  ngOnInit() {
    this.language = "ES";
    this.maxResult = 6;
    this.onLoadStudents = false;
    this.es = {
            firstDayOfWeek: 1,
            dayNames: [ "domingo","lunes","martes","miércoles","jueves","viernes","sábado" ],
            dayNamesShort: [ "dom","lun","mar","mié","jue","vie","sáb" ],
            dayNamesMin: [ "D","L","M","X","J","V","S" ],
            monthNames: [ "enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre" ],
            monthNamesShort: [ "ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic" ]
        };
    this.selectedStudent = new Student();
    this.students = new Array<Student>();
    this.inListStudent = true;
    this.inCreateStudent = false;
    this.inUpdateStudent = false;
    this.moreStudents = true;
    this.loading = false;
    this.loadInitData();
    this.loadPaymentPlans();
  }

  private loadInitData(){
    this.showFooterLoading();
    this.loadDivisions();
  }

  private showFooterLoading(){
    this.loading = true;
  }

  private hideFooterLoading(){
    this.loading = false;
  }

  loadDivisions(){
    this.showLoading();
    this.divisionService.loadDivisions()
       .subscribe(
        data => this.loadDivisionsOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion divisionService...')
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
    this.loadLevels();
  }

  loadLevels(){
    this.levelService.loadLevels()
       .subscribe(
        data => this.loadLevelsOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion levelService...')
    );
  }

  loadLevelsOK(data){
    this.filterLevelsOptions = new Array<SelectItem>();
    this.filterLevelsOptions.push({label:"Seleccionar Nivel", value:null});
    for(let levelJSON of data.body.result){
      let level: Level = new Level();
      Object.assign(level, levelJSON);
      this.filterLevelsOptions.push({label:level.description, value:level});
    }
    this.loadYears();
  }

  loadYears(){
    this.yearService.loadYears()
       .subscribe(
        data => this.loadYearsOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion yearService...')
    );
  }

  loadYearsOK(data){
    this.filterYearOptions = new Array<SelectItem>();
    this.filterYearOptions.push({label:"Seleccionar Año", value:null});
    for(let yearJSON of data.body.result){
      let year: Year = new Year();
      Object.assign(year, yearJSON);
      this.filterYearOptions.push({label:year.description, value:year});
    }
    this.loadDocumentType();
  }

  loadStudentsOK(data){
    Logger.debug("Estudiantes recibidos.. " + JSON.stringify(data));
    if((data.body.result != null) && (data.body.result.length > 0)){
      if(this.students == null){
        this.students = new Array<Student>();
      }
      for(let studentJSON of data.body.result){
        Logger.debug("Cargando estudiantes recibidos...");
        let student: Student = new Student();
        Object.assign(student, studentJSON);
        student.course = this.getCourse(studentJSON.course);
        this.students.push(student);
      }
    }
    Logger.debug("Estudiantes cargados.. " + JSON.stringify(this.students));
    this.hideFooterLoading();
    this.hideLoading();
    this.onLoadStudents = false;
  }

  goListCreate(goList: boolean) {
    if(this.havePaymentsPlans){
      this.inUpdateStudent = false;
      this.inCreateStudent = !goList;
      this.inListStudent = goList;
      this.students = null;
      this.rows = 0;
      if(goList){
        this.selectedStudent = null;
      }
      this.loadInitData();
      this.loadStudents();
    }else{
      this.messageErrorService = "Para crear un estudiante debe primero crear por lo menos 1 plan de pago. "; 
      this.showMessageError = true;
      this.showMessageErrorService = true;
    }
  }

  goListUpdate(goList: boolean){
    this.inCreateStudent = false;
    this.inUpdateStudent = !goList;
    this.inListStudent = goList;
    this.students = null;
    if(goList){
        this.selectedStudent = null;
    }
    this.loadInitData();
  }

  goListPaymentRecord(goList: boolean){
    this.inCreateStudent = false;
    this.inUpdateStudent = false;
    this.showPaymentRecord = !goList;
    this.inListStudent = goList;
    this.loadInitData();
  }

  private loadPaymentPlans(){
    this.paymentPlanService.loadPaymentPlans()
       .subscribe(
        data => this.loadPaymentPlansOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion paymentPlanService...')
    );
  }

  private loadPaymentPlansOK(data){
    Logger.debug("Planes de pago recuperados: " + JSON.stringify(data.body.result));
    Logger.debug("Cantidad de Planes: " + data.body.result.length);
    if(data.body.result.length > 0){
      this.havePaymentsPlans = true;
    }else{
      this.havePaymentsPlans = false;
    }
  }

  loadData(event) {
    this.showFooterLoading();
    if(this.students != null){
      this.rows = this.students.length;
    }else{
      this.rows = 0;
    }
    if(this.courses != null){
      this.loadStudents();    
    }else{
      this.loadCourses();
    }
  }

  loadStudents(){
    if(!this.onLoadStudents){
      this.onLoadStudents = true;
      this.studentService.loadStudents(this.rows, this.maxResult)
              .subscribe(
                data => this.loadStudentsOK(data),
                err => this.loadErrorLoadStudentService(err),
                () => Logger.debug('Termino ejecucion studentService...')
        );
    }
  }

  loadStudent(student: Student){
    this.selectedStudent = student;
    if(!this.inPaymentRecord){
      this.inUpdateStudent = true;
      this.inListStudent = false;
      this.inCreateStudent = false;
    }else{
      this.showPaymentRecord = true;
      this.inUpdateStudent = false;
      this.inListStudent = false;
      this.inCreateStudent = false;
      Logger.debug("Emitiendo select student go record");
      this.selectStudent.emit(student);
      this.goRecord.emit(true);
    }
  }

  loadErrorStudentMessageService(error){
    this.loadErrorMessageService(error);
  }

  loadErrorLoadStudentService(error){
    this.onLoadStudents = false;
    this.loadErrorMessageService(error);
  }

  loadErrorMessageService(error){
    Logger.warn("Ocurrio un error al crear un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
    this.hideLoading();
  }

  showLoading(){
    this.loading = true;
  }

  hideLoading(){
    this.loading = false;
  }

  private loadDocumentType(){
    this.documentTypeService.loadDocumentTypes()
       .subscribe(
        data => this.loadDocumentTypeOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }
  
  private loadDocumentTypeOK(data){
    this.documentTypes = new Array<DocumentType>();
    for(let documentTypeJSON of data.body.result){
      let documentType: DocumentType = new DocumentType();
      Object.assign(documentType, documentTypeJSON);
      this.documentTypes.push(documentType);
    }
    this.hideLoading();
  }

  private loadCourses(){
    this.courseService.loadCourses()
       .subscribe(
        data => this.loadCoursesOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  private loadCoursesOK(data){
    this.courses = new Array<Course>();
    this.filterCourseOptions = new Array<SelectItem>();
    for(let courseJSON of data.body.result){
      let course: Course = new Course();
      Object.assign(course, courseJSON);
      let description = course.level.description + " " + course.year.description + "° " + course.division.description;
      this.filterCourseOptions.push({label:description, value:course});
      this.courses.push(course);
    }
    Logger.debug("Cursos cargados: " + JSON.stringify(this.courses));
    this.loadStudents();
  }

  getDocumentTypeDescription(id: number){
    if(this.documentTypes != null){
      for(let documentType of this.documentTypes){
        if(documentType.id == id){
          return documentType.description;
        }
      }
    }
  }

  getCourse(id){
    Logger.debug("Buscando course:" + id);
    for(let course of this.courses){
      if(id == course.id){
        Logger.debug("Se encontro el curso: " + id);
        return course;
      }
    }
    Logger.debug("No se encontro el curso: " + id);
    return null;
  }

  showReportModal(){
    if((this.students != null) && (this.students.length > 0)){
      this.displayReportPopUp = "block";
    }else{
      this.messageErrorService = "No puede generar un informe si no tiene alumnos ingresados.";
      this.showMessageError = true;
      this.showMessageErrorService = true;
    }
  }

  hideReportModal(){
    this.displayReportPopUp = "none";
  }

  downloadReport(){

  }

}