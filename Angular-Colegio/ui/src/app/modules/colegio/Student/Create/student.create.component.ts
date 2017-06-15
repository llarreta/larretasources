//Angular
import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';

//Models
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { ObligationStatus } from '../../Models/ObligationStatus.model';
import { Responsible } from '../../Models/Responsible.model';
import { DocumentType } from '../../Models/DocumentType.model';

//Commons
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';

//Service
import { StudentService } from '../../services/student.service';
import { DocumentTypeService } from '../../services/documentType.service';

//ngPrime
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'school-students-create',
  templateUrl: './src/app/modules/colegio/Student/Create/student.create.component.html'
})
export class StudentCreateComponent implements OnInit{

  @Output()
  goList = new EventEmitter();
  @Input()
  student: Student;
  @Input()
  inEdit: Boolean;

  inputName: InputModel;
  inputSurname: InputModel;
  inputDocumentNumber: InputModel;
  inputEmail: InputModel;
  documentTypes: Array<SelectItem>;

  paymentPlans: Array<PaymentPlan>;
  obligationsStatus: Array<ObligationStatus>;
  responsibles: Array<Responsible>;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;
  displayPopUp: string;
  displayLoading: string;

  constructor(private studentService: StudentService, private documentTypeService: DocumentTypeService) {}

  ngOnInit() {
    this.loadInitData();
    if(!this.inEdit){
      this.student = new Student();
    }
    this.initInputs();
    //Aca deberia cargar con los servicios todas las listas
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar.";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento."
  }

  private loadInitData(){
    this.documentTypeService.loadDocumentTypes()
       .subscribe(
        data => this.loadDocumentTypeOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadDocumentTypeOK(data){
    this.documentTypes = new Array<SelectItem>();
    this.documentTypes.push({label:"Seleccionar Tipo Documento", value:null});
    for(let documentTypeJSON of data.body.result){
      let documentType: DocumentType = new DocumentType();
      Object.assign(documentType, documentTypeJSON);
      this.documentTypes.push({label:documentType.description, value:documentType.id});
    }
  }

  initInputs(){
    this.inputName = new InputModel();
    this.inputName.id=  "name";
    this.inputName.labelContent= "Nombre";
    this.inputName.messageErrorEmpty= "Debe completar el nombre.";
    this.inputName.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputName.required= true;
    this.inputName.type= "text";
    this.inputName.validationActivate = true;
    
    this.inputDocumentNumber = new InputModel();
    this.inputDocumentNumber.id= "document-number";
    this.inputDocumentNumber.labelContent= "Numero de Documento";
    this.inputDocumentNumber.messageErrorEmpty= "Debe completar el numero de documento.";
    this.inputDocumentNumber.messageErrorValidation= "El numero de documento es invalido.";
    this.inputDocumentNumber.required= true;
    this.inputDocumentNumber.type= "number";
    this.inputDocumentNumber.maskText= "99-9999-99";
    this.inputDocumentNumber.validationActivate = true;
    this.inputDocumentNumber.maskActivate = false;

    this.inputEmail = new InputModel();
    this.inputEmail.id= "email";
    this.inputEmail.labelContent= "E-mail";
    this.inputEmail.messageErrorEmpty= "Debe completar el email.";
    this.inputEmail.messageErrorValidation="El email ingresado es invalido.";
    this.inputEmail.required= true;
    this.inputEmail.type= "email";
    this.inputEmail.validationActivate = true;

    this.inputSurname = new InputModel();
    this.inputSurname.id= "surname"
    this.inputSurname.labelContent= "Apellido";
    this.inputSurname.messageErrorEmpty= "Debe completar el apellido.";
    this.inputSurname.messageErrorValidation= "El apellido ingresado es invalido.";
    this.inputSurname.required= true;
    this.inputSurname.type= "text";
    this.inputSurname.validationActivate = true;

    if(this.inEdit){
      this.initValueInput();
    }
  }

  initValueInput(){
    this.inputName.value = this.student.name;
    this.inputDocumentNumber.value = String(this.student.documentNumber);
    this.inputEmail.value = this.student.email;
    this.inputSurname.value = this.student.surname;
  }

  isAllOK(){
    if(this.inputDocumentNumber.isAllOK && this.inputEmail.isAllOK 
      && this.inputName.isAllOK && this.inputSurname.isAllOK){
        return true;
    }else{
      return false;
    }
  }

  confirm(){
    if(this.isAllOK()){
      this.loadStudentData();
      this.showMessageError = false;
      let datosResponse;
      let status;
      if(!this.inEdit){
        this.showLoading();
        this.studentService.createStudent(this.student)
        .subscribe(
          data => this.createStudentOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }else{
        this.showLoading();
        this.studentService.updateStudent(this.student)
        .subscribe(
          data => this.createStudentOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }
    }
  }

  createStudentOK(data){
    this.hideLoading();
    this.goToList();
  }

  loadErrorMessageService(error){
    this.hideLoading();
    Logger.warn("Ocurrio un error al crear o actualizar un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
    Logger.error(JSON.stringify(error));
  }

  loadStudentData(){
    this.student.name = this.inputName.value;
    this.student.surname = this.inputSurname.value;
    this.student.documentNumber = Number(this.inputDocumentNumber.value);
  }

  deleteSelectedStudent(){
    this.showLoading();
    this.studentService.deleteStudent(this.student)
       .subscribe(
        data => this.deleteStudentOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion studentService...')
    );
  }

  deleteStudentOK(data){
    Logger.debug("Estudiante eliminado...");
    this.goToList();
    this.hideLoading();
  }

  showLoading(){
    this.displayLoading = "block";
  }

  hideLoading(){
    this.displayLoading = "none";
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

  setStudentName(inputModel: InputModel){
    this.inputName = inputModel;
  }
  setStudentSurname(inputModel: InputModel){
    this.inputSurname = inputModel;
  }
  setDocumentNumber(inputModel: InputModel){
    this.inputDocumentNumber = inputModel;
  }
  setEmail(inputModel: InputModel){
    this.inputEmail = inputModel;
  }
}