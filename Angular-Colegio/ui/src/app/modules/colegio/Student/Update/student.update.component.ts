import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { ObligationStatus } from '../../Models/ObligationStatus.model';
import { Responsible } from '../../Models/Responsible.model';
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { StudentService } from '../../services/student.service';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'school-students-update',
  templateUrl: './src/app/modules/colegio/Student/Update/student.update.component.html'
})
export class StudentUpdateComponent implements OnInit{

  
  @Output()
  goList = new EventEmitter();
  @Input()
  student: Student;
  studentAux: Student;

  inputName: InputModel;
  inputSurname: InputModel;
  inputDocumentNumber: InputModel;
  inputEmail: InputModel;
  documentTypes: SelectItem[];
  editStudent: boolean;
  
  paymentPlans: Array<PaymentPlan>;
  obligationsStatus: Array<ObligationStatus>;
  responsibles: Array<Responsible>;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  constructor(private studentService: StudentService) {}

  ngOnInit() {
    this.cloneStudent();
    //Aca deberia cargar con los servicios todas las listas
    this.loadPaymentPlansTest();
    this.loadObligationsStatusTest();
    this.loadResponsiblesTest();
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar.";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento."
    this.editStudent = false;
    this.inicializarInputs();
  }

  cloneStudent(){
    this.studentAux = new Student();
    this.studentAux.course = this.student.course;
    this.studentAux.description = this.student.description;
    this.studentAux.documentNumber = this.student.documentNumber;
    this.studentAux.documentType = this.student.documentType;
    this.studentAux.email = this.student.email;
    this.studentAux.id = this.student.id;
    this.studentAux.name = this.student.name;
    this.studentAux.obligationsStatus = this.student.obligationsStatus;
    this.studentAux.paymentPlans = this.student.paymentPlans;
    this.studentAux.responsibles = this.student.responsibles;
    this.studentAux.surname = this.student.surname;
  }

  inicializarInputs(){
    
    this.inputName = new InputModel();
    this.inputName.id=  "name";
    this.inputName.labelContent= "Nombre";
    this.inputName.messageErrorEmpty= "Debe completar el nombre.";
    this.inputName.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputName.required= true;
    this.inputName.type= "text";
    this.inputName.validationActivate = true;
    this.inputName.value = this.student.name;

    this.inputDocumentNumber = new InputModel();
    this.inputDocumentNumber.id= "document-number";
    this.inputDocumentNumber.labelContent= "Numero de Documento";
    this.inputDocumentNumber.messageErrorEmpty= "Debe completar el numero de documento.";
    this.inputDocumentNumber.messageErrorValidation= "El numero de documento es invalido.";
    this.inputDocumentNumber.required= true;
    this.inputDocumentNumber.type= "number";
    this.inputDocumentNumber.maskText= "**-**";
    this.inputDocumentNumber.validationActivate = true;
    this.inputDocumentNumber.value = String(this.student.documentNumber);

    this.inputEmail = new InputModel();
    this.inputEmail.id= "email";
    this.inputEmail.labelContent= "E-mail";
    this.inputEmail.messageErrorEmpty= "Debe completar el email.";
    this.inputEmail.messageErrorValidation="El email ingresado es invalido.";
    this.inputEmail.required= true;
    this.inputEmail.type= "email";
    this.inputEmail.validationActivate = true;
    this.inputEmail.value = this.student.email;

    this.inputSurname = new InputModel();
    this.inputSurname.id= "surname"
    this.inputSurname.labelContent= "Apellido";
    this.inputSurname.messageErrorEmpty= "Debe completar el apellido.";
    this.inputSurname.messageErrorValidation= "El apellido ingresado es invalido.";
    this.inputSurname.required= true;
    this.inputSurname.type= "text";
    this.inputSurname.validationActivate = true;
    this.inputSurname.value = this.student.surname;

    this.documentTypes = [];
    this.documentTypes.push({label:'Tipo Documento', value:null});
    this.documentTypes.push({label:'CUIL', value:"CUIL"});
    this.documentTypes.push({label:'DNI', value:"DNI"});
    this.documentTypes.push({label:'PASAPORTE', value:"PASAPORTE"});
    

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
      this.studentService.createStudent(this.student)
       .subscribe(
        data => this.createStudentOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
      );
      
    }else{
      this.showMessageError = true;
      this.showMessageErrorInput = true;
    }
  }

  createStudentOK(data){
    this.goToList();
  }

  loadErrorMessageService(error){
    Logger.warn("Ocurrio un error al crear un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }

  loadStudentData(){
    this.student.name = this.inputName.value;
    this.student.surname = this.inputSurname.value;
    this.student.documentNumber = Number(this.inputDocumentNumber.value);
  }

  loadPaymentPlansTest(){}
  loadObligationsStatusTest(){}
  loadResponsiblesTest(){}

  editStudentActive(){
    this.inputName.value
    this.editStudent = true;
  }

  goToList(){
    this.showMessageError = false;
    if(!this.editStudent){
      this.goList.emit(true);
    }else{
      this.rollback();
      this.editStudent = false;
    }
  }

  rollback(){
    this.student = this.studentAux;
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