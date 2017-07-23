//Angular
import { Component, Input, Output, OnInit, OnDestroy, EventEmitter, ViewChild } from '@angular/core';

//Models
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { ObligationStatus } from '../../Models/ObligationStatus.model';
import { Responsible } from '../../Models/Responsible.model';
import { DocumentType } from '../../Models/DocumentType.model';

//Commons
import { InputModel } from '../../Commons/Input/input.model.component';
import { MaskTemplates } from '../../Commons/Input/mask.templates';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';

//Service
import { StudentService } from '../../services/student.service';
import { DocumentTypeService } from '../../services/documentType.service';
import { PaymentPlanService } from '../../services/paymentPlan.service';
import { CourseService } from '../../services/course.service';

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
  @ViewChild("nameComponent")
  nameComponent: InputCommonsComponent;
  @ViewChild("surnameComponent")
  surnameComponent: InputCommonsComponent;
  @ViewChild("documentNumberComponent")
  documentNumberComponent: InputCommonsComponent;
  @ViewChild("emailComponent")
  emailComponent: InputCommonsComponent;

  inputName: InputModel;
  inputSurname: InputModel;
  inputDocumentNumber: InputModel;
  inputEmail: InputModel;
  documentTypes: Array<SelectItem>;

  obligationsStatus: Array<ObligationStatus>;
  responsibles: Array<Responsible>;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  
  errorDocumentType: boolean;
  errorCourses: boolean;
  errorPaymentPlans: boolean;
  isPaymentPlansOK: boolean;
  isCoursesOK: boolean;
  isDocumentTypeOK: boolean;

  messageErrorInputs: string; 
  messageErrorService: string;
  displayPopUp: string;
  loading: boolean;

  paymentPlansListBox: SelectItem[];
  coursesListBox: SelectItem[];

  maxResult: number;
  result: number;

  uploadedPhoto: any[] = [];

  constructor(
              private studentService: StudentService, 
              private documentTypeService: DocumentTypeService,
              private paymentPlanService: PaymentPlanService,
              private courseService: CourseService
              ) {}

  ngOnInit() {
    this.hideLoading();
    this.loadInitData();
    if(!this.inEdit){
      this.student = new Student();
    }
    this.initInputs();
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar.";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento."
  }

  private loadInitData(){
    this.showLoading();
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
    this.paymentPlanService.loadPaymentPlans().subscribe(
        data => this.loadPaymentPlansOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadPaymentPlansOK(data){
    this.paymentPlansListBox = new Array<SelectItem>();
    for(let paymentPlanJSON of data.body.result){
      let paymentPlan: PaymentPlan = new PaymentPlan();
      Object.assign(paymentPlan, paymentPlanJSON);
      this.paymentPlansListBox.push({label:paymentPlan.description, value:paymentPlan.id});
    }
    Logger.debug("payments plans cargados: " + JSON.stringify(this.paymentPlansListBox));
    this.courseService.loadCourses().subscribe(
        data => this.loadCoursesOK(data),
        err => this.loadErrorMessageService(err),
        () => console.log('Vacio')
    );
  }

  loadCoursesOK(data){
    this.coursesListBox = new Array<SelectItem>();
    for(let courseJSON of data.body.result){
      let course: Course = new Course();
      Object.assign(course, courseJSON);
      let label = "";
      label += course.year.description + " " + course.division.description + " " + course.level.description; 
      this.coursesListBox.push({label:label, value:course});
    }
    this.hideLoading();
  }

  initInputs(){
    this.inputName = new InputModel();
    this.inputName.id=  "name";
    this.inputName.labelContent= "Nombre";
    this.inputName.messageErrorEmpty= "Debe completar el nombre.";
    this.inputName.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputName.messageErrorMinCharacter= "El nombre debe tener 4 letras minimo."
    this.inputName.required= true;
    this.inputName.type= "text";
    this.inputName.validationActivate = true;
    this.inputName.minCharacter = 3;
    
    this.inputDocumentNumber = new InputModel();
    this.inputDocumentNumber.id= "document-number";
    this.inputDocumentNumber.labelContent= "Numero de Documento";
    this.inputDocumentNumber.messageErrorEmpty= "Debe completar el numero de documento.";
    this.inputDocumentNumber.messageErrorValidation= "El numero de documento es invalido.";
    this.inputDocumentNumber.required= true;
    this.inputDocumentNumber.type= "dni";
    this.inputDocumentNumber.mask= MaskTemplates.DNI;
    this.inputDocumentNumber.validationActivate = true;
    this.inputDocumentNumber.maskActivate = true;
    this.inputDocumentNumber.messageErrorMinCharacter = "El numero de documento esta incompleto.";
    this.inputDocumentNumber.messageErrorTypeText = "El dni ingresado es invalido.";

    this.inputEmail = new InputModel();
    this.inputEmail.id= "email";
    this.inputEmail.labelContent= "E-mail";
    this.inputEmail.messageErrorEmpty= "Debe completar el email.";
    this.inputEmail.messageErrorValidation="El email ingresado es invalido.";
    this.inputEmail.required= true;
    this.inputEmail.type= "mail";
    this.inputEmail.validationActivate = true;
    this.inputEmail.mask = MaskTemplates.MAIL;
    this.inputEmail.maskActivate = true;
    this.inputEmail.messageErrorMinCharacter = "Ingrese un email valido.";
    this.inputEmail.messageErrorTypeText = "El email ingresado es invalido."

    this.inputSurname = new InputModel();
    this.inputSurname.id= "surname"
    this.inputSurname.labelContent= "Apellido";
    this.inputSurname.messageErrorEmpty= "Debe completar el apellido.";
    this.inputSurname.messageErrorValidation= "El apellido ingresado es invalido.";
    this.inputSurname.required= true;
    this.inputSurname.type= "text";
    this.inputSurname.validationActivate = true;
    this.inputSurname.minCharacter = 3;
    this.inputSurname.messageErrorMinCharacter= "El apellido debe tener 4 letras minimo."

    if(this.inEdit){
      this.initValueInput();
    }else{
      this.hideLoading();
    } 

  }

  initValueInput(){
    this.inputName.value = this.student.name;
    this.inputDocumentNumber.value = String(this.student.documentNumber);
    this.inputEmail.value = this.student.email;
    this.inputSurname.value = this.student.surname;
    this.hideLoading();
  }

  isAllOK(){
    if((this.inputDocumentNumber.isAllOK) && (this.inputEmail.isAllOK)
      && (this.inputName.isAllOK) && (this.inputSurname.isAllOK)
      && (this.student.documentType != null) && (this.student.course != null)
      && (this.student.paymentPlans != null) && (this.student.paymentPlans.length > 0)){
        return true;
    }else{
      this.nameComponent.checkValue();
      this.surnameComponent.checkValue();
      this.emailComponent.checkValue();
      this.documentNumberComponent.checkValue();
      if(this.student.documentType == null){
        this.errorDocumentType = true;
      }else{
        this.errorDocumentType = false;
      }
      if(this.student.course == null){
        this.errorCourses = true;
      }else{
        this.errorCourses = false;
      }
      if((this.student.paymentPlans == null) || (this.student.paymentPlans.length <= 0)){
        this.errorPaymentPlans = true;
      }else{
        this.errorPaymentPlans = false;
      }
      return false;
    }
  }

  confirm(){
    this.showLoading();
    if(this.isAllOK()){
      this.loadStudentData();
      this.showMessageError = false;
      let datosResponse;
      let status;
      if(!this.inEdit){
        
        this.studentService.createStudent(this.student)
        .subscribe(
          data => this.createStudentOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }else{
        
        this.studentService.updateStudent(this.student)
        .subscribe(
          data => this.createStudentOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }
    }else{
      this.loadErrorMessageInput("Debe completar todos los campos.");
    }
  }

  createStudentOK(data){
    this.goToList();
  }

  loadErrorMessageInput(error){
    this.hideLoading();
    Logger.warn("Error de validacion en abm estudiante...");
    this.messageErrorInputs = error;
    this.showMessageErrorInput = true;
    this.showMessageError = true;
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
    this.student.documentNumber = this.inputDocumentNumber.value;
    this.student.email = this.inputEmail.value;
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
  }

  paymentPlanChange(){
    if((this.student.paymentPlans != null) && (this.student.paymentPlans.length > 0)){
      this.isPaymentPlansOK = true;
      this.errorPaymentPlans = false;
    }else{
      this.isPaymentPlansOK = false;
      this.errorPaymentPlans = true;
    }
  }

  coursesChange(){
    if(this.student.course != null){
      this.errorCourses = false;
      this.isCoursesOK = true;
    }else{
      this.errorCourses = true;
      this.isCoursesOK = false;
    }
  }

  documentTypeChange(){
    if(this.student.documentType != null){
      this.isDocumentTypeOK = true;
      this.errorDocumentType = false;
    }else{
      this.isDocumentTypeOK = false;
      this.errorDocumentType = true;
    }
  }

  showLoading(){
    this.loading = true;
    Logger.debug("ShowLoading");
  }

  hideLoading(){
    this.loading = false;
    Logger.debug("HideLoading");
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