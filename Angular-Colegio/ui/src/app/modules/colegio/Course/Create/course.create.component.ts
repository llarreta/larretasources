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

  inputDescription: InputModel;
  levels: Array<SelectItem>;
  years: Array<SelectItem>;
  divisions: Array<SelectItem>;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  constructor(private courseService: CourseService, private levelService: LevelService,
              private divisionService: DivisionService, private yearService: YearService) {}

  ngOnInit() {
    this.loadInitData();
    if(!this.inEdit){
      this.course = new Course();
    }
    this.initInputs();
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar. ";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento. "
  }

  loadInitData(){
    this.divisionService.loadDivisions()
       .subscribe(
        data => this.loadDivisionsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
    this.levelService.loadLevels()
       .subscribe(
        data => this.loadLevelsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
    this.yearService.loadYears()
       .subscribe(
        data => this.loadYearsOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadDivisionsOK(data){
    this.divisions = new Array<SelectItem>();
    for(let divisionJSON of data.body.result){
      let division: Division = new Division();
      Object.assign(division, divisionJSON);
      this.divisions.push({label:division.description, value:division});
    }
  }

  loadLevelsOK(data){
    this.levels = new Array<SelectItem>();
    for(let levelJSON of data.body.result){
      let level: Level = new Level();
      Object.assign(level, levelJSON);
      this.levels.push({label:level.description, value:level});
    }
  }

  loadYearsOK(data){
    this.years = new Array<SelectItem>();
    for(let yearJSON of data.body.result){
      let year: Year = new Year();
      Object.assign(year, yearJSON);
      this.years.push({label:year.description, value:year});
    }
  }

  initInputs(){
    this.inputDescription = new InputModel();
    this.inputDescription.id=  "name";
    this.inputDescription.labelContent= "Nombre";
    this.inputDescription.messageErrorEmpty= "Debe indicar el nombre.";
    this.inputDescription.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputDescription.required= true;
    this.inputDescription.type= "text";
    this.inputDescription.validationActivate = true;
    if(this.inEdit){
      this.inputDescription.value = this.course.description;
    }
  }

  isAllOK(){
    if(this.inputDescription.isAllOK && this.course.level != null 
      && this.course.year != null && this.course.division != null){
        return true;
    }else{
      if(!this.inputDescription.isAllOK){
        this.messageErrorInputs += this.inputDescription.messageErrorEmpty + ". ";
      }
      if(this.course.level == null){
        this.messageErrorInputs += "Debe seleccionar un nivel. ";
      }
      if(this.course.year == null){
        this.messageErrorInputs += "Debe seleccionar un año. ";
      }
      if(this.course.division == null){
        this.messageErrorInputs += "Debe seleccionar un division. ";
      }
      this.showMessageErrorInput = true;
      this.showMessageError = true;
      return false;
    }
  }

  confirm(){
    if(this.isAllOK()){
      this.loadCourseData();
      this.showMessageError = false;
      let datosResponse;
      let status;
      if(!this.inEdit){
        this.courseService.createCourse(this.course)
          .subscribe(
          data => this.createCourseOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }else{
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
  }

  loadCourseData(){
    this.course.description = this.inputDescription.value;
  }

  goToList(){
    this.goList.emit(true);
  }

  setCourseDescription(inputModel: InputModel){
    this.inputDescription = inputModel;
  }
  
}