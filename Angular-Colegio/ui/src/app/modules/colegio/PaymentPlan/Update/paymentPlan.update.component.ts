import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
//import { PaymentPlanService } from '../../services/paymentPlan.service';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'school-paymentPlan-update',
  templateUrl: './src/app/modules/colegio/PaymentPlan/Update/paymentPlan.update.component.html'
})
export class PaymentPlanUpdateComponent implements OnInit{

  
  @Output()
  goList = new EventEmitter();
  @Input()
  paymentPlan: PaymentPlan;
  paymentPlanAux: PaymentPlan;

  inputDivision: InputModel;
  levels: SelectItem[];
  years: SelectItem[];
  editPaymentPlan: boolean;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  //constructor(private paymentPlanService: PaymentPlanService) {}
    constructor() {}

  ngOnInit() {
    this.clonePaymentPlan();
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar.";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento."
    this.editPaymentPlan = false;
    this.inicializarInputs();
  }

  clonePaymentPlan(){
    this.paymentPlanAux = new PaymentPlan();
    Logger.info("curso seleccionado: " + this.paymentPlan);
    this.paymentPlanAux.description = this.paymentPlan.description;
    this.paymentPlanAux.id = this.paymentPlan.id;
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
    this.years.push({label:'2°', value:2});
    this.years.push({label:'3°', value:3});
    this.years.push({label:'4°', value:4});
    this.years.push({label:'5°', value:5});
    this.years.push({label:'6°', value:6});
    this.years.push({label:'7°', value:7});
    this.years.push({label:'8°', value:8});

  }

  isAllOK(){
    
  }

  confirm(){
    if(this.isAllOK()){
      this.loadPaymentPlanData();
      this.showMessageError = false;
      let datosResponse;
      let status;
      //this.paymentPlanService.createPaymentPlan(this.paymentPlan)
      // .subscribe(
      //  data => this.createPaymentPlanOK(data),
      //  err => this.loadErrorMessageService(err),
      //  () => console.log('Vacio')
      //);
      
    }else{
      this.showMessageError = true;
      this.showMessageErrorInput = true;
    }
  }

  createPaymentPlanOK(data){
    this.goToList();
  }

  loadErrorMessageService(error){
    Logger.warn("Ocurrio un error al crear un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }

  loadPaymentPlanData(){
  }

  editPaymentPlanActive(){
    this.editPaymentPlan = true;
  }

  goToList(){
    this.showMessageError = false;
    if(!this.editPaymentPlan){
      this.goList.emit(true);
    }else{
      this.rollback();
      this.editPaymentPlan = false;
    }
  }

  rollback(){
    this.paymentPlan = this.paymentPlanAux;
  }

  setPaymentPlanDivision(inputModel: InputModel){
    this.inputDivision = inputModel;
  }

}