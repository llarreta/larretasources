import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { ObligationStatus } from '../../Models/ObligationStatus.model';
import { Responsible } from '../../Models/Responsible.model';
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';

@Component({
  selector: 'colegio-alumnos-create',
  templateUrl: './src/app/modules/colegio/Student/Create/student.create.component.html'
})
export class StudentCreateComponent implements OnInit{

  @Output()
  goList = new EventEmitter();

  inputName: InputModel;
  inputSurname: InputModel;
  inputDocumentNumber: InputModel;
  inputEmail: InputModel;

  student: Student;
  paymentPlans: Array<PaymentPlan>;
  obligationsStatus: Array<ObligationStatus>;
  responsibles: Array<Responsible>;

  constructor() {}

  ngOnInit() {
    this.student = new Student();
    this.inicializarInputs();
    //Aca deberia cargar con los servicios todas las listas
    this.loadPaymentPlansTest();
    this.loadObligationsStatusTest();
    this.loadResponsiblesTest();
  }

  inicializarInputs(){
    
    this.inputName = new InputModel();
    this.inputName.id=  "name";
    this.inputName.isErrorValidation= false;
    this.inputName.labelContent= "Nombre";
    this.inputName.messageErrorEmpty= "Debe completar el nombre.";
    this.inputName.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputName.required= true;
    this.inputName.type= "text";

    this.inputDocumentNumber = new InputModel();
    this.inputDocumentNumber.id= "document-number";
    this.inputDocumentNumber.isErrorValidation= false;
    this.inputDocumentNumber.labelContent= "Numero de Documento";
    this.inputDocumentNumber.messageErrorEmpty= "Debe completar el numero de documento.";
    this.inputDocumentNumber.messageErrorValidation= "El numero de documento es invalido.";
    this.inputDocumentNumber.required= true;
    this.inputDocumentNumber.type= "number";

    this.inputEmail = new InputModel();
    this.inputEmail.id= "email";
    this.inputEmail.isErrorValidation= false;
    this.inputEmail.labelContent= "E-mail";
    this.inputEmail.messageErrorEmpty= "Debe completar el email.";
    this.inputEmail.messageErrorValidation="El email ingresado es invalido.";
    this.inputEmail.required= true;
    this.inputEmail.type= "email";

    this.inputSurname = new InputModel();
    this.inputSurname.id= "surname"
    this.inputSurname.isErrorValidation= false;
    this.inputSurname.labelContent= "Apellido";
    this.inputSurname.messageErrorEmpty= "Debe completar el apellido.";
    this.inputSurname.messageErrorValidation= "El apellido ingresado es invalido.";
    this.inputSurname.required= true;
    this.inputSurname.type= "text";

  }

  loadPaymentPlansTest(){}
  loadObligationsStatusTest(){}
  loadResponsiblesTest(){}

  goToList(){
    this.goList.emit(true);
  }

  setStudentName(name: string){
    this.student.name = name;
  }
  setStudentSurname(surname: string){
    this.student.surname = surname;
  }
  setDocumentNumber(documentNumber: number){
    this.student.documentNumber = documentNumber;
  }
  setEmail(email: string){
    this.student.email = email;
  }
}