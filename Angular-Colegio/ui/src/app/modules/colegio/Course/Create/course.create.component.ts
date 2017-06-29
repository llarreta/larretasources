//Angular
import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';

//Models
import { Course } from '../../Models/Course.model';
import { Division } from '../../Models/Division.model';
import { Level } from '../../Models/Level.model';
import { Year } from '../../Models/Year.model';

//Commons
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';

//ngPrime
import { SelectItem } from 'primeng/primeng';

//Service
import { CourseService } from '../../services/course.service';
import { LevelService } from '../../services/level.service';
import { YearService } from '../../services/year.service';
import { DivisionService } from '../../services/division.service';

@Component({
  selector: 'school-course-create',
  templateUrl: './src/app/modules/colegio/Course/Create/course.create.component.html'
})
export class CourseCreateComponent implements OnInit{

  @Output()
  goList = new EventEmitter();
  @Input()
  inEdit: Boolean;
  @Input()
  course: Course;

  levels: Array<SelectItem>;
  years: Array<SelectItem>;
  divisions: Array<SelectItem>;

  displayPopUp: string;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  errorLevel: boolean;
  errorYear: boolean;
  errorDivision: boolean;

  constructor(private courseService: CourseService, private levelService: LevelService,
              private divisionService: DivisionService, private yearService: YearService) {}

  ngOnInit() {
    this.loadInitData();
    if(!this.inEdit){
      this.course = new Course();
    }
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar. ";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento. "
  }

  loadInitData(){
    Logger.debug("entrando al crear cursos");
    this.showLoading();
    this.loadDivisions();
  }

  loadDivisions(){
    this.divisionService.loadDivisions()
       .subscribe(
        data => this.loadDivisionsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadDivisionsOK(data){
    this.divisions = new Array<SelectItem>();
    this.divisions.push({label:"Seleccionar Division", value:null});
    for(let divisionJSON of data.body.result){
      let division: Division = new Division();
      Object.assign(division, divisionJSON);
      this.divisions.push({label:division.description, value:division});
    }
    this.loadLevels();
  }

  loadLevels(){
    this.showLoading();
    this.levelService.loadLevels()
       .subscribe(
        data => this.loadLevelsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadLevelsOK(data){
    this.levels = new Array<SelectItem>();
    this.levels.push({label:"Seleccionar Nivel", value:null});
    for(let levelJSON of data.body.result){
      let level: Level = new Level();
      Object.assign(level, levelJSON);
      this.levels.push({label:level.description, value:level});
    }
    this.loadYears();
  }

  loadYears(){
    this.showLoading();
    this.yearService.loadYears()
       .subscribe(
        data => this.loadYearsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadYearsOK(data){
    this.years = new Array<SelectItem>();
    this.years.push({label:"Seleccionar Año", value:null});
    for(let yearJSON of data.body.result){
      let year: Year = new Year();
      Object.assign(year, yearJSON);
      this.years.push({label:year.description, value:year});
    }
    this.hideLoading();
  }

  showLoading(){
    Logger.debug("Emitiendo evento mostrar loading");
    //this.inLoading.emit(true);
  }

  hideLoading(){
    //this.inLoading.emit(false);
  }

  isAllOK(){
    if(this.course.level != null 
      && this.course.year != null && this.course.division != null){
        return true;
    }else{
      this.messageErrorInputs = "";
      if(this.course.level == null){
        this.messageErrorInputs += "Debe seleccionar un nivel. ";
        this.errorLevel = true;
      }else{
        this.messageErrorInputs.replace("Debe seleccionar un nivel. ", "");
        this.errorLevel = false;
      }
      if(this.course.year == null){
        this.messageErrorInputs += "Debe seleccionar un año. ";
        this.errorYear = true;
      }else{
        this.errorYear = false;
        this.messageErrorInputs.replace("Debe seleccionar un año. ", "");
      }
      if(this.course.division == null){
        this.messageErrorInputs += "Debe seleccionar un division. ";
        this.errorDivision = true;
      }else{
        this.errorDivision = false;
        this.messageErrorInputs.replace("Debe seleccionar un division. ", "");
      }
      this.showMessageErrorInput = true;
      this.showMessageError = true;
      return false;
    }
  }

  confirm(){
    if(this.isAllOK()){
      this.showMessageError = false;
      let datosResponse;
      let status;
      if(!this.inEdit){
        this.showLoading();
        this.courseService.createCourse(this.course)
          .subscribe(
          data => this.createCourseOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }else{
        this.showLoading();
        this.courseService.updateCourse(this.course)
          .subscribe(
          data => this.createCourseOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }
    }
  }

  createCourseOK(data){
    this.goToList();
  }

  loadErrorMessageService(error){
    Logger.warn("Ocurrio un error al crear un curso...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
    this.hideLoading();
  }

  goToList(){
    this.goList.emit(true);
  }

  confirmDelete(){
    this.displayPopUp = "block";
  }

  hideDisplayPopUp(){
    this.displayPopUp = "none";
  }

  deleteSelectedCourse(){
    this.showLoading();
    this.courseService.deleteCourse(this.course)
       .subscribe(
        data => this.deleteCourseOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion courseService...')
    );
  }

  deleteCourseOK(data){
    Logger.debug("Curso eliminado...");
    this.goToList();
    this.hideLoading();
  }

  getCourseDescription(){
    let description: string = "";
    if(this.course != null){
      if(this.course.year != null){
        description += this.course.year.description + " ";
      }
      if(this.course.division != null){
        description += this.course.division.description + " ";
      }
      if(this.course.level != null){
        description += this.course.level.description;
      }
    }
    return description;
  }

}