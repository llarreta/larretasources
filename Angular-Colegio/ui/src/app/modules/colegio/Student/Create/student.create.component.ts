//Angular
import { Component, Input, Output, OnInit, OnDestroy, EventEmitter, ViewChild } from '@angular/core';

//Models
import { Student } from '../../Models/Student.model';
import { Course } from '../../Models/Course.model';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { ObligationStatus } from '../../Models/ObligationStatus.model';
import { Responsible } from '../../Models/Responsible.model';
import { DocumentType } from '../../Models/DocumentType.model';
import { Email } from '../../Models/Email.model';
import { Country } from '../../Models/Country.model';
import { State } from '../../Models/State.model';
import { Location } from '../../Models/Location.model';
import { Telephone } from '../../Models/Telephon.model';
import { AddressP } from '../../Models/Address.model';

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
import { CountryService } from '../../services/country.service';
import { LocationService } from '../../services/location.service';
import { StateService } from '../../services/state.service';

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
  @ViewChild("telphoneComponent")
  telphoneComponent: InputCommonsComponent;
  @ViewChild("codeComponent")
  codeComponent: InputCommonsComponent;

  inputName: InputModel;
  inputSurname: InputModel;
  inputDocumentNumber: InputModel;
  inputEmail: InputModel;
  inputTelphone: InputModel;
  inputCode: InputModel;
  documentTypes: Array<SelectItem>;

  obligationsStatus: Array<ObligationStatus>;
  responsibles: Array<Responsible>;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  
  errorDocumentType: boolean;
  errorNationality: boolean;
  errorCourses: boolean;
  errorPaymentPlans: boolean;
  isPaymentPlansOK: boolean;
  isCoursesOK: boolean;
  isDocumentTypeOK: boolean;
  isNationalityOK: boolean;

  messageErrorInputs: string; 
  messageErrorService: string;
  displayPopUp: string;
  loading: boolean;

  paymentPlansListBox: SelectItem[];
  coursesListBox: SelectItem[];
  nationalityListBox: SelectItem[];
  countrysListBox: SelectItem[];
  locationsListBox: SelectItem[];
  statesListBox: SelectItem[];

  maxResult: number;
  result: number;

  es: any;

  constructor(
              private studentService: StudentService, 
              private documentTypeService: DocumentTypeService,
              private paymentPlanService: PaymentPlanService,
              private courseService: CourseService,
              private countryService: CountryService,
              private locationService: LocationService,
              private stateService: StateService
              ) {}

  ngOnInit() {
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
    this.es = { 
            firstDayOfWeek: 1,
            dayNames: [ "domingo","lunes","martes","miércoles","jueves","viernes","sábado" ],
            dayNamesShort: [ "dom","lun","mar","mié","jue","vie","sáb" ],
            dayNamesMin: [ "D","L","M","X","J","V","S" ],
            monthNames: [ "enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre" ],
            monthNamesShort: [ "ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic" ]
        };
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
    this.countryService.loadCountrys().subscribe(
      data => this.loadCountrysOK(data),
      err => this.loadErrorMessageService(err),
      () => console.log('Vacio')
    );
  }

  loadCountrysOK(data){
    this.countrysListBox = new Array<SelectItem>();
    this.nationalityListBox = new Array<SelectItem>();
    this.coursesListBox.push({label:"Seleccionar Pais", value:null});
    this.nationalityListBox.push({label:"Seleccionar Nacionalidad", value:null});
    for(let countryJSON of data.body.result){
      let country: Country = new Country();
      Object.assign(country, countryJSON);
      let label = country.description; 
      this.coursesListBox.push({label:label, value:country.id});
      this.nationalityListBox.push({label:label, value:country.id});
    }
    this.courseService.loadCourses(null, null).subscribe(
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

    this.inputTelphone = new InputModel();
    this.inputTelphone.id=  "telphone";
    this.inputTelphone.labelContent= "Telefono";
    this.inputTelphone.messageErrorEmpty= "Debe completar el telefono.";
    this.inputTelphone.messageErrorValidation= "El telefono ingresado es invalido."
    this.inputTelphone.required= true;
    this.inputTelphone.type= "text";
    this.inputTelphone.validationActivate = true;
    this.inputTelphone.mask= MaskTemplates.TELPHONE;
    this.inputTelphone.maskActivate = true;

    this.inputCode = new InputModel();
    this.inputCode.id=  "code";
    this.inputCode.labelContent= "Codigo de Alumno";
    this.inputCode.messageErrorEmpty= "Debe completar el codigo de alumno.";
    this.inputCode.messageErrorValidation= "El codigo ingresado es invalido."
    this.inputCode.messageErrorMinCharacter= "El codigo debe tener 5 caracteres minimo."
    this.inputCode.required= true;
    this.inputCode.type= "text";
    this.inputCode.validationActivate = true;
    this.inputCode.minCharacter = 4;

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
    }

  }

  initValueInput(){
    this.inputName.value = this.student.name;
    this.inputDocumentNumber.value = String(this.student.documentNumber);
    this.inputEmail.value = this.student.emails[0].email.address;
    this.inputSurname.value = this.student.surname;
    this.inputTelphone.value = this.student.telephones[0].telephone.number;
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
        this.isDocumentTypeOK = false;
      }else{
        this.errorDocumentType = false;
        this.isDocumentTypeOK = true;
      }
      if(this.student.nationality == null){
        this.errorNationality = true;
        this.isNationalityOK = false;
      }else{
        this.errorNationality = false;
        this.isNationalityOK = true;
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
    this.student.code = this.inputCode.value;

    if((this.student.telephones != null) && (this.student.telephones.length > 0)){
      this.student.telephones[0].telephone.number = this.inputTelphone.value;
    }else{
      this.student.telephones = new Array<Telephone>();
      let telephone: Telephone = new Telephone();
      telephone.telephoneType = 1;
      telephone.createInstanceTelephoneNumber();
      telephone.telephone.number = this.inputTelphone.value;
      this.student.telephones.push(telephone);
    }
    if((this.student.emails != null) && (this.student.emails.length > 0)){
      this.student.emails[0].email.address = this.inputEmail.value;
    }else{
      this.student.emails = new Array<Email>();
      let email: Email = new Email();
      email.emailType = 1;
      email.createInstanceEmailAddress();
      email.email.address = this.inputEmail.value;
      this.student.emails.push(email);
    }
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

  onPhotoSelect(files){
    console.log("files:" + files);
    let file: File = files[0];  
    var photoReader: FileReader = new FileReader();
    photoReader.onloadend = (e) => {
      this.student.photo = photoReader.result;
    }
    photoReader.readAsDataURL(file);
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

  nationalityChange(){
    if(this.student.nationality != null){
      this.isNationalityOK = true;
      this.errorNationality = false;
    }else{
      this.isNationalityOK = false;
      this.errorNationality = true;
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
  setStudentCode(inputModel: InputModel){
    this.inputCode = inputModel;
  }
  setTelphone(inputModel: InputModel){
    this.inputTelphone = inputModel;
  }
}