import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { ObligationStatus } from '../../Models/ObligationStatus.model';
import { Responsible } from '../../Models/Responsible.model';
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { SelectOneMenuCommonsComponent } from '../../Commons/SelectOneMenu/selectOneMenu.component';
import { SelectOneMenuModel } from '../../Commons/SelectOneMenu/selectOneMenu.model.component';
import { OptionModel } from '../../Commons/SelectOneMenu/option.model.component';

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
  selecOneMenuDocumentType: SelectOneMenuModel;

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

    this.selecOneMenuDocumentType = new SelectOneMenuModel();
    this.selecOneMenuDocumentType.id= "document-type";
    this.selecOneMenuDocumentType.listOptions = new Array<OptionModel>();
    
    let dniOption: OptionModel = new OptionModel();
    dniOption.id= 1;
    dniOption.label= "DNI";

    let cuilOption: OptionModel = new OptionModel();
    cuilOption.id= 2;
    cuilOption.label= "CUIL";

    let pasaporteOption: OptionModel = new OptionModel();
    pasaporteOption.id= 3;
    pasaporteOption.label= "PASAPORTE";

    this.selecOneMenuDocumentType.listOptions.push(dniOption);
    this.selecOneMenuDocumentType.listOptions.push(cuilOption);
    this.selecOneMenuDocumentType.listOptions.push(pasaporteOption);

    this.selecOneMenuDocumentType.messageErrorEmpty= "Debe seleccionar un tipo de documento.";
    this.selecOneMenuDocumentType.nonSelectionOptionMessage= "Tipo de documento";
    
    if(this.student.documentType != null){
      if(this.student.documentType.includes("DNI")){
        this.selecOneMenuDocumentType.optionSelected = dniOption;
      }
      if(this.student.documentType.includes("CUIL")){
        this.selecOneMenuDocumentType.optionSelected = cuilOption;
      }
      if(this.student.documentType.includes("PASAPORTE")){
        this.selecOneMenuDocumentType.optionSelected = pasaporteOption;
      }
    }

    this.selecOneMenuDocumentType.required = true;

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

  setDocumentType(id: number){
    
    switch(id){
      
      case 0:
        this.student.documentType = "";
      break;

      case 1:
        this.student.documentType = "DNI";
      break;

      case 2:
        this.student.documentType = "CUIL";
      break;

      case 3:
        this.student.documentType = "PASAPORTE";
      break;

    }
    
  }
}