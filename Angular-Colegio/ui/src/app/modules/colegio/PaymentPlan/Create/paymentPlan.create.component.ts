 import { Component, Input, Output, OnInit, OnDestroy, EventEmitter } from '@angular/core';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { Obligation } from '../../Models/Obligation.model';
import { Price } from '../../Models/Price.model';
import { Detail } from '../../Models/Detail.model';
import { LittleDetail } from '../../Models/LittleDetail.model';
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
//import { PaymentPlanService } from '../../services/paymentPlan.service';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';
import { SelectItem } from 'primeng/primeng';

@Component({
  selector: 'school-paymentPlan-create',
  templateUrl: './src/app/modules/colegio/PaymentPlan/Create/paymentPlan.create.component.html'
})
export class PaymentPlanCreateComponent implements OnInit{

  @Output()
  goList = new EventEmitter();

  inputDescription: InputModel;

  paymentPlan: PaymentPlan;

  es: any;
  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  //constructor(private paymentPlanService: PaymentPlanService) {}

  constructor() {}

  ngOnInit() {
    this.paymentPlan = new PaymentPlan();
    this.inicializarInputs();
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
        }
  }

  inicializarInputs(){
    
    this.inputDescription = new InputModel();
    this.inputDescription.id=  "descripcion";
    this.inputDescription.labelContent= "Descripcion";
    this.inputDescription.messageErrorEmpty= "Debe indicar la descripcion.";
    this.inputDescription.messageErrorValidation= "La descripcion ingresada es invalida."
    this.inputDescription.required= true;
    this.inputDescription.type= "text";
    this.inputDescription.validationActivate = true;

  }

  isAllOK(){
    if((this.inputDescription.isAllOK) && (this.paymentPlan.obligations != null) && (this.paymentPlan.obligations.length > 0)){
        return true;
    }else{
      return false;
    }
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
    this.paymentPlan.description = this.inputDescription.value;
  }

  goToList(){
    this.goList.emit(true);
  }

  setPaymentPlanDescription(inputModel: InputModel){
    this.inputDescription = inputModel;
  }

  addNewObligation(){
    Logger.debug("Creando nueva cuota...");
    if(this.paymentPlan.obligations == null){
      this.paymentPlan.obligations = new Array<Obligation>();
    }
    this.paymentPlan.obligations.push(new Obligation());
    Logger.debug("Logueando paymentPlan");
    Logger.debug(JSON.stringify(this.paymentPlan));
  }

  addNewPrice(obligation: Obligation){
    Logger.debug("Creando nuevo precio...");
    if(obligation.prices == null){
      obligation.prices = new Array<Price>();
    }
    obligation.prices.push(new Price());
    Logger.debug("Logueando paymentPlan");
    Logger.debug(JSON.stringify(this.paymentPlan));
  }

  addNewDetail(price: Price){
    Logger.debug("Creando nuevo detalle...");
    if(price.details == null){
      price.details = new Array<Detail>();
    }
    price.details.push(new Detail());
    Logger.debug("Logueando paymentPlan");
    Logger.debug(JSON.stringify(this.paymentPlan));
    Logger.debug(this.paymentPlan.valueOf());
  }

  addNewLittleDetail(detail: Detail){
    Logger.debug("Creando nuevo detalle especifico...");
    if(detail.littleDetails == null){
      detail.littleDetails = new Array<LittleDetail>();
    }
    detail.littleDetails.push(new LittleDetail());
    Logger.debug("Logueando paymentPlan");
    Logger.debug(JSON.stringify(this.paymentPlan));
  }

  stopPropagationHeader(event){
    event.stopPropagation();
  }

}