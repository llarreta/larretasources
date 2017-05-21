 import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { Course } from '../../Models/Course.model';
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
//import { CourseService } from '../../services/course.service';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'school-course-create',
  templateUrl: './src/app/modules/colegio/Course/Create/course.create.component.html'
})
export class CourseCreateComponent implements OnInit{

  @Output()
  goList = new EventEmitter();

  inputDivision: InputModel;
  levels: SelectItem[];
  years: SelectItem[];

  course: Course;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  //constructor(private courseService: CourseService) {}

  constructor() {}

  ngOnInit() {
    this.course = new Course();
    this.inicializarInputs();
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar.";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento."
  }

  inicializarInputs(){
    
    this.inputDivision = new InputModel();
    this.inputDivision.id=  "division";
    this.inputDivision.labelContent= "Division";
    this.inputDivision.messageErrorEmpty= "Debe indicar la division.";
    this.inputDivision.messageErrorValidation= "La division ingresada es invalida."
    this.inputDivision.required= true;
    this.inputDivision.type= "text";
    this.inputDivision.validationActivate = true;

    this.levels = [];
    this.levels.push({label:'Nivel', value:null});
    this.levels.push({label:'Inicial', value:"INICIAL"});
    this.levels.push({label:'Primario', value:"PRIMARIO"});
    this.levels.push({label:'Secundario', value:"SECUNDARIO"});
    this.levels.push({label:'Superior', value:"SUPERIOR"});

    this.years = [];
    this.years.push({label:'Año', value:null});
    this.years.push({label:'1°', value:1});
    this.years.push({label:'2°', value:1});
    this.years.push({label:'3°', value:1});
    this.years.push({label:'4°', value:1});
    this.years.push({label:'5°', value:1});
    this.years.push({label:'6°', value:1});
    this.years.push({label:'7°', value:1});
    this.years.push({label:'8°', value:1});

  }

  isAllOK(){
    if(this.inputDivision.isAllOK && this.course.level != null && this.course.year != null){
        return true;
    }else{
      return false;
    }
  }

  confirm(){
    if(this.isAllOK()){
      this.loadCourseData();
      this.showMessageError = false;
      let datosResponse;
      let status;
      //this.courseService.createCourse(this.course)
      // .subscribe(
      //  data => this.createCourseOK(data),
      //  err => this.loadErrorMessageService(err),
      //  () => console.log('Vacio')
      //);
      
    }else{
      this.showMessageError = true;
      this.showMessageErrorInput = true;
    }
  }

  createCourseOK(data){
    this.goToList();
  }

  loadErrorMessageService(error){
    Logger.warn("Ocurrio un error al crear un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }

  loadCourseData(){
    this.course.division = this.inputDivision.value;
  }

  loadPaymentPlansTest(){}
  loadObligationsStatusTest(){}
  loadResponsiblesTest(){}

  goToList(){
    this.goList.emit(true);
  }

  setCourseDivision(inputModel: InputModel){
    this.inputDivision = inputModel;
  }
  
}