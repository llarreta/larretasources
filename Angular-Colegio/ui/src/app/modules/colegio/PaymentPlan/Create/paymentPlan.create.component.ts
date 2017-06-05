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
  inputObligationDescription: InputModel;
  inputPriceDescription: InputModel;
  inputPriceValue: InputModel;
  inputDetailDescription: InputModel;
  inputDetailValue: InputModel;
  inputLittleDetailDescription: InputModel;
  inputLittleDetailValue: InputModel;

  paymentPlan: PaymentPlan;

  obligationSelected: Obligation;
  priceSelected: Price;
  detailSelected: Detail;
  littleDetailSelected: LittleDetail;

  es: any;
  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  messageErrorInputs: string; 
  messageErrorService: string;

  displayPopUp: string;
  obligationContentActive: Boolean;
  priceContentActive: Boolean;
  detailContentActive: Boolean;
  littleDetailContentActive: Boolean;

  //constructor(private paymentPlanService: PaymentPlanService) {}

  constructor() {}

  ngOnInit() {
    this.initVariablesSelected();
    this.hideDisplayPopUp();
    this.obligationContentActive = false;
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.littleDetailContentActive = false;
    this.paymentPlan = new PaymentPlan();
    this.inicializarInputs();
    this.showMessageError = false;
    this.showMessageErrorInput = false;
    this.showMessageErrorService = false;
    this.messageErrorInputs = "Debes corregir todos los errores antes de continuar.";
    this.messageErrorService = "Ocurrio un error de conexion con el servidor, reintentelo en un momento.";
    this.es = {
            firstDayOfWeek: 1,
            dayNames: [ "domingo","lunes","martes","miércoles","jueves","viernes","sábado" ],
            dayNamesShort: [ "dom","lun","mar","mié","jue","vie","sáb" ],
            dayNamesMin: [ "D","L","M","X","J","V","S" ],
            monthNames: [ "enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre" ],
            monthNamesShort: [ "ene","feb","mar","abr","may","jun","jul","ago","sep","oct","nov","dic" ]
        };
  }

  initVariablesSelected(){
    this.obligationSelected = new Obligation();
    this.priceSelected = new Price();
    this.detailSelected = new Detail();
    this.littleDetailSelected = new LittleDetail();
  }

  showDisplayPopUp(){
    this.obligationSelected = new Obligation();
    this.inputObligationDescription.value = "";
    this.inputObligationDescription.isAllOK = false;
    this.displayPopUp = "block";
    this.obligationContentActive = true;
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.littleDetailContentActive = false;
  }

  hideDisplayPopUp(){
    this.displayPopUp = "none";
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

    this.inputObligationDescription = new InputModel();
    this.inputObligationDescription.id=  "descripcion";
    this.inputObligationDescription.labelContent= "Descripcion";
    this.inputObligationDescription.messageErrorEmpty= "Debe indicar la descripcion.";
    this.inputObligationDescription.messageErrorValidation= "La descripcion ingresada es invalida."
    this.inputObligationDescription.required= true;
    this.inputObligationDescription.type= "text";
    this.inputObligationDescription.validationActivate = true;

    this.inputPriceDescription = new InputModel();
    this.inputPriceDescription.id=  "descripcion";
    this.inputPriceDescription.labelContent= "Descripcion";
    this.inputPriceDescription.messageErrorEmpty= "Debe indicar la descripcion.";
    this.inputPriceDescription.messageErrorValidation= "La descripcion ingresada es invalida."
    this.inputPriceDescription.required= true;
    this.inputPriceDescription.type= "text";
    this.inputPriceDescription.validationActivate = true;

    this.inputPriceValue = new InputModel();
    this.inputPriceValue.id=  "priceValue";
    this.inputPriceValue.labelContent= "Precio";
    this.inputPriceValue.messageErrorEmpty= "Debe indicar el precio.";
    this.inputPriceValue.messageErrorValidation= "El precio ingresado es invalido."
    this.inputPriceValue.required= true;
    this.inputPriceValue.type= "number";
    this.inputPriceValue.validationActivate = true;

    this.inputDetailValue = new InputModel();
    this.inputDetailValue.id=  "priceValue";
    this.inputDetailValue.labelContent= "Precio";
    this.inputDetailValue.messageErrorEmpty= "Debe indicar el precio.";
    this.inputDetailValue.messageErrorValidation= "El precio ingresado es invalido."
    this.inputDetailValue.required= true;
    this.inputDetailValue.type= "number";
    this.inputDetailValue.validationActivate = true;

    this.inputDetailDescription = new InputModel();
    this.inputDetailDescription.id=  "descripcion";
    this.inputDetailDescription.labelContent= "Descripcion";
    this.inputDetailDescription.messageErrorEmpty= "Debe indicar la descripcion.";
    this.inputDetailDescription.messageErrorValidation= "La descripcion ingresada es invalida."
    this.inputDetailDescription.required= true;
    this.inputDetailDescription.type= "text";
    this.inputDetailDescription.validationActivate = true;

    this.inputLittleDetailDescription = new InputModel();
    this.inputLittleDetailDescription.id=  "descripcion";
    this.inputLittleDetailDescription.labelContent= "Descripcion";
    this.inputLittleDetailDescription.messageErrorEmpty= "Debe indicar la descripcion.";
    this.inputLittleDetailDescription.messageErrorValidation= "La descripcion ingresada es invalida."
    this.inputLittleDetailDescription.required= true;
    this.inputLittleDetailDescription.type= "text";
    this.inputLittleDetailDescription.validationActivate = true;

    this.inputLittleDetailValue = new InputModel();
    this.inputLittleDetailValue.id=  "priceValue";
    this.inputLittleDetailValue.labelContent= "Precio";
    this.inputLittleDetailValue.messageErrorEmpty= "Debe indicar el precio.";
    this.inputLittleDetailValue.messageErrorValidation= "El precio ingresado es invalido."
    this.inputLittleDetailValue.required= true;
    this.inputLittleDetailValue.type= "number";
    this.inputLittleDetailValue.validationActivate = true;

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
  
  addNewObligation(){
    Logger.debug("Creando nueva cuota...");
    if(this.paymentPlan.obligations == null){
      this.paymentPlan.obligations = new Array<Obligation>();
    }
    this.paymentPlan.obligations.push(new Obligation());
    Logger.debug("Logueando paymentPlan");
    Logger.debug(JSON.stringify(this.paymentPlan));
  }

  addNewPrice(event, obligation: Obligation){
    Logger.debug("Event: "+event);
    Logger.debug("Target: "+event.target.nodeName)
    Logger.debug("Parent: "+event.target.parentElement.nodeName);
    Logger.debug("Parent Parent: "+event.target.parentElement.parentElement.nodeName);
    Logger.debug("Parent Parent Parent: "+event.target.parentElement.parentElement.parentElement.nodeName);
    event.target.parentElement.parentElement.setAttribute("aria-expanded", "true");
    event.target.parentElement.parentElement.setAttribute("aria-selected", "true");
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

  newPrice(){
    this.priceSelected = new Price();
    this.priceContentActive = true;
    this.detailContentActive = false;
    this.obligationContentActive = false;
    this.littleDetailContentActive = false;
  }

  newDetail(){
    this.detailSelected = new Detail();
    this.priceContentActive = false;
    this.detailContentActive = true;
    this.obligationContentActive = false;
    this.littleDetailContentActive = false;
  }

  newLittleDetail(){
    this.littleDetailSelected = new LittleDetail();
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.obligationContentActive = false;
    this.littleDetailContentActive = true;
  }

  backToObligation(){
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.obligationContentActive = true;
    this.littleDetailContentActive = false;
  }

  backToDetail(){
    this.priceContentActive = false;
    this.detailContentActive = true;
    this.obligationContentActive = false;
    this.littleDetailContentActive = false;
  }

  savePrice(){
    this.priceSelected.description = this.inputPriceDescription.value;
    this.priceSelected.value = Number(this.inputPriceValue.value);
    if(this.obligationSelected.prices == null){
      this.obligationSelected.prices = new Array<Price>();
    }
    this.obligationSelected.prices.push(this.priceSelected);
    this.backToObligation();
  }

  setPaymentPlanDescription(inputModel: InputModel){
    this.inputDescription = inputModel;
  }

  setPaymentPlanObligationDescription(inputModel: InputModel){
    this.inputObligationDescription = inputModel;
  }

  setPaymentPlanObligationPriceDescription(inputModel: InputModel){
    this.inputPriceDescription = inputModel;
  }

  setPaymentPlanObligationPriceValue(inputModel: InputModel){
    this.inputPriceValue = inputModel;
  }

  setPaymentPlanObligationPriceDetailDescription(inputModel: InputModel){
    this.inputDetailDescription = inputModel;
  }

  setPaymentPlanObligationPriceDetailValue(inputModel: InputModel){
    this.inputDetailValue = inputModel;
  }

  setPaymentPlanObligationPriceLittleDetailDescription(inputModel: InputModel){
    this.inputLittleDetailDescription = inputModel;
  }

  setPaymentPlanObligationPriceLittleDetailValue(inputModel: InputModel){
    this.inputLittleDetailValue = inputModel;
  }

}