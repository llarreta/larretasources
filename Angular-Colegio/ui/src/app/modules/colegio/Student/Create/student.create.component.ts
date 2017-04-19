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
import { DocumentTypes } from '../../Commons/Enums/DocumentTypes';
import { StudentService } from '../../services/student.service';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';

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

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  constructor(private studentService: StudentService) {}

  ngOnInit() {
    this.student = new Student();
    this.inicializarInputs();
    //Aca deberia cargar con los servicios todas las listas
    this.loadPaymentPlansTest();
    this.loadObligationsStatusTest();
    this.loadResponsiblesTest();
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar.";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento."
  }

  inicializarInputs(){
    
    this.inputName = new InputModel();
    this.inputName.id=  "name";
    this.inputName.labelContent= "Nombre";
    this.inputName.messageErrorEmpty= "Debe completar el nombre.";
    this.inputName.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputName.required= true;
    this.inputName.type= "text";

    this.inputDocumentNumber = new InputModel();
    this.inputDocumentNumber.id= "document-number";
    this.inputDocumentNumber.labelContent= "Numero de Documento";
    this.inputDocumentNumber.messageErrorEmpty= "Debe completar el numero de documento.";
    this.inputDocumentNumber.messageErrorValidation= "El numero de documento es invalido.";
    this.inputDocumentNumber.required= true;
    this.inputDocumentNumber.type= "number";
    this.inputDocumentNumber.maskText= "**-**";

    this.inputEmail = new InputModel();
    this.inputEmail.id= "email";
    this.inputEmail.labelContent= "E-mail";
    this.inputEmail.messageErrorEmpty= "Debe completar el email.";
    this.inputEmail.messageErrorValidation="El email ingresado es invalido.";
    this.inputEmail.required= true;
    this.inputEmail.type= "email";

    this.inputSurname = new InputModel();
    this.inputSurname.id= "surname"
    this.inputSurname.labelContent= "Apellido";
    this.inputSurname.messageErrorEmpty= "Debe completar el apellido.";
    this.inputSurname.messageErrorValidation= "El apellido ingresado es invalido.";
    this.inputSurname.required= true;
    this.inputSurname.type= "text";

    this.selecOneMenuDocumentType = new SelectOneMenuModel();
    this.selecOneMenuDocumentType.id= "document-type";
    this.selecOneMenuDocumentType.listOptions = new Array<OptionModel>();
    
    let dniOption: OptionModel = new OptionModel();
    dniOption.id= DocumentTypes.DNI;
    dniOption.label= "DNI";

    let cuilOption: OptionModel = new OptionModel();
    cuilOption.id= DocumentTypes.CUIL;
    cuilOption.label= "CUIL";

    let pasaporteOption: OptionModel = new OptionModel();
    pasaporteOption.id= DocumentTypes.PASAPORTE;
    pasaporteOption.label= "PASAPORTE";

    let sarasaOption: OptionModel = new OptionModel();
    sarasaOption.id= DocumentTypes.PASAPORTE;
    sarasaOption.label= "SARASA";

    this.selecOneMenuDocumentType.listOptions.push(dniOption);
    this.selecOneMenuDocumentType.listOptions.push(cuilOption);
    this.selecOneMenuDocumentType.listOptions.push(pasaporteOption);
    this.selecOneMenuDocumentType.listOptions.push(sarasaOption);

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

  isAllOK(){
    if(this.inputDocumentNumber.isAllOK && this.inputEmail.isAllOK 
      && this.inputName.isAllOK && this.inputSurname.isAllOK
      && this.selecOneMenuDocumentType.isAllOK){
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

  goToList(){
    this.goList.emit(true);
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
  setDocumentType(selectOneModel: SelectOneMenuModel){
    this.selecOneMenuDocumentType = selectOneModel;
  }
}