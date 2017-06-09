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
  inputDetailDescription: InputModel;
  inputDetailValue: InputModel;
  inputLittleDetailDescription: InputModel;
  inputLittleDetailValue: InputModel;
  dateObligation: Date;

  paymentPlan: PaymentPlan;

  obligationSelected: Obligation;
  priceSelected: Price;
  detailSelected: Detail;
  littleDetailSelected: LittleDetail;

  es: any;
  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;
  showMessageErrorPopUp:boolean = false;
  messageErrorInputs: string; 
  messageErrorService: string;
  messageErrorInputsPopUp: string;

  displayPopUp: string;
  obligationContentActive: Boolean;
  priceContentActive: Boolean;
  detailContentActive: Boolean;
  littleDetailContentActive: Boolean;

  obligations: Array<SelectItem>;
  details: Array<SelectItem>;
  littleDetails: Array<SelectItem>;

  inEditPopUp: boolean = false;

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

  refreshListBox(){
    Logger.debug("refreshlistbox");
    this.obligations = new Array<SelectItem>();
    this.details = new Array<SelectItem>();
    this.littleDetails = new Array<SelectItem>();
    if((this.paymentPlan.obligations != null) && (this.paymentPlan.obligations.length > 0)){
      for(let obligation of this.paymentPlan.obligations){
        this.obligations.push({label:obligation.description, value:obligation});
      }
      if((this.obligationSelected != null) && (this.obligationSelected.prices != null) && (this.obligationSelected.prices.length > 0)){
        for(let price of this.obligationSelected.prices){
          if((price.details != null) && (price.details.length > 0)){
            for(let detail of price.details){
              this.details.push({label:detail.description, value:detail});
            }
            if((this.detailSelected != null) && (this.detailSelected.littleDetails != null) 
              && (this.detailSelected.littleDetails.length > 0)){
              for(let littleDetail of this.detailSelected.littleDetails){
                this.littleDetails.push({label:littleDetail.description, value:littleDetail});
              }
            }
          }
        }
      }
    }
    Logger.debug("obligations: " + JSON.stringify(this.obligations));
  }

  initVariablesSelected(){
    this.obligationSelected = new Obligation();
    this.priceSelected = new Price();
    this.detailSelected = new Detail();
    this.littleDetailSelected = new LittleDetail();
  }

  showDisplayPopUpObligation(){
    this.obligationSelected = new Obligation();
    this.inputObligationDescription.value = "";
    this.dateObligation = new Date();
    this.inputObligationDescription.isAllOK = false;
    this.displayPopUp = "block";
    this.obligationContentActive = true;
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.littleDetailContentActive = false;
    this.inEditPopUp = false;
  }

  showDisplayPopUpDetail(){
    this.displayPopUp = "block";
    this.inputDetailDescription.value = "";
    this.inputDetailDescription.isAllOK = false;
    this.inputDetailValue.isAllOK = false;
    this.inEditPopUp = false;
    if((this.detailSelected.littleDetails != null) && (this.detailSelected.littleDetails.length > 0)){
      this.inputLittleDetailValue.disabled = true;
      let totalPrice: number = 0;
      for(let littleDetail of this.detailSelected.littleDetails){
        totalPrice += littleDetail.value;
      }
      this.inputDetailValue.value = String(totalPrice);
    }else{
      this.inputLittleDetailValue.disabled = false;
      this.inputDetailValue.value = "";
    }
    this.newDetail();
  }

  showDisplayPopUpLittleDetail(){
    this.inEditPopUp = false;
    this.displayPopUp = "block";
    this.inputLittleDetailDescription.value = "";
    this.inputLittleDetailDescription.isAllOK = false;
    this.inputLittleDetailValue.value = "";
    this.inputLittleDetailValue.isAllOK = false;
    this.newLittleDetail();
  }

  obligationSelectedChange(){
    this.detailSelected = new Detail();
    this.littleDetailSelected = new LittleDetail();
    this.refreshListBox();
  }

  detailSelectedChange(){
    this.littleDetailSelected = new LittleDetail();
    this.refreshListBox();
  }

  hideDisplayPopUp(){
    this.displayPopUp = "none";
  }

  inicializarInputs(){
    
    this.inputDescription = new InputModel();
    this.inputDescription.id=  "namePaymentPlan";
    this.inputDescription.labelContent= "Nombre";
    this.inputDescription.messageErrorEmpty= "Debe indicar el nombre.";
    this.inputDescription.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputDescription.required= true;
    this.inputDescription.type= "text";
    this.inputDescription.validationActivate = true;

    this.inputObligationDescription = new InputModel();
    this.inputObligationDescription.id=  "namePaymentPlanObligation";
    this.inputObligationDescription.labelContent= "Nombre";
    this.inputObligationDescription.messageErrorEmpty= "Debe indicar el nombre.";
    this.inputObligationDescription.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputObligationDescription.required= true;
    this.inputObligationDescription.type= "text";
    this.inputObligationDescription.validationActivate = true;

    this.inputDetailValue = new InputModel();
    this.inputDetailValue.id=  "detailValue";
    this.inputDetailValue.labelContent= "Valor";
    this.inputDetailValue.messageErrorEmpty= "Debe indicar el valor.";
    this.inputDetailValue.messageErrorValidation= "El valor ingresado es invalido."
    this.inputDetailValue.required= true;
    this.inputDetailValue.type= "number";
    this.inputDetailValue.validationActivate = true;

    this.inputDetailDescription = new InputModel();
    this.inputDetailDescription.id=  "namePaymentPlanDetail";
    this.inputDetailDescription.labelContent= "Nombre";
    this.inputDetailDescription.messageErrorEmpty= "Debe indicar el nombre.";
    this.inputDetailDescription.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputDetailDescription.required= true;
    this.inputDetailDescription.type= "text";
    this.inputDetailDescription.validationActivate = true;

    this.inputLittleDetailDescription = new InputModel();
    this.inputLittleDetailDescription.id=  "namePaymentPlanLittleDetail";
    this.inputLittleDetailDescription.labelContent= "Nombre";
    this.inputLittleDetailDescription.messageErrorEmpty= "Debe indicar el nombre.";
    this.inputLittleDetailDescription.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputLittleDetailDescription.required= true;
    this.inputLittleDetailDescription.type= "text";
    this.inputLittleDetailDescription.validationActivate = true;

    this.inputLittleDetailValue = new InputModel();
    this.inputLittleDetailValue.id=  "priceValueLittleDetail";
    this.inputLittleDetailValue.labelContent= "Valor";
    this.inputLittleDetailValue.messageErrorEmpty= "Debe indicar el valor.";
    this.inputLittleDetailValue.messageErrorValidation= "El valor ingresado es invalido."
    this.inputLittleDetailValue.required= true;
    this.inputLittleDetailValue.type= "number";
    this.inputLittleDetailValue.validationActivate = true;

  }

  isAllOK(){
    if((this.inputDescription.isAllOK) && (this.paymentPlan.obligations != null) 
      && (this.paymentPlan.obligations.length > 0) && (this.ifObligationsHaveOneDetail())){
        return true;
    }else{
      this.messageErrorInputs = "";
      if(!this.inputDescription.isAllOK){
        this.messageErrorInputs += "Debe ingresar un nombre para el plan de pago."
      }
      if((this.paymentPlan.obligations == null) || (this.paymentPlan.obligations.length <= 0)){
        this.messageErrorInputs += " Debe ingresar al menos una cuota para crear un plan de pago."
      }
      if(!this.ifObligationsHaveOneDetail()){
        this.messageErrorInputs += " Debe ingresar al menos una detalle por cuota."
      }
      this.showMessageError = true;
      this.showMessageErrorInput = true;
      return false;
    }
  }

  ifObligationsHaveOneDetail(){
    for(let obligation of this.paymentPlan.obligations){
      for(let price of obligation.prices){
        if((price.details == null) || (price.details.length <= 0)){
          return false;
        }
      }
    }
    return true;
  }

  saveSelectedObligation(){
    if((this.inputObligationDescription.isAllOK) && (this.dateObligation != null) &&
        (!this.isDuplicateDescriptionObligation(this.inputObligationDescription.value))){
      if(this.paymentPlan.obligations == null){
        this.paymentPlan.obligations = new Array<Obligation>();
      }
      this.obligationSelected.description = this.inputObligationDescription.value;
      this.obligationSelected.dueDate = this.dateObligation;
      this.paymentPlan.obligations.push(this.obligationSelected);
      this.refreshListBox();
      this.hideDisplayPopUp();
    }else{
      this.showMessageErrorPopUp = true;
      if(this.dateObligation == null){
        this.messageErrorInputsPopUp += "Debe ingresar una fecha."
      }
      if(!this.inputObligationDescription.isAllOK){
        this.messageErrorInputsPopUp += " Debe ingresar un nombre."
      }
      if(this.isDuplicateDescriptionObligation(this.inputObligationDescription.value)){
        this.messageErrorInputsPopUp += " El nombre ingresado ya existe."
      }
    }
  }

  saveSelectedObligationEdit(){
    if((this.inputObligationDescription.isAllOK) && (this.dateObligation != null) &&
        (!this.isDuplicateDescriptionObligationEdit(this.inputObligationDescription.value))){
      this.obligationSelected.description = this.inputObligationDescription.value;
      this.obligationSelected.dueDate = this.dateObligation;
      this.refreshListBox();
      this.hideDisplayPopUp();
    }else{
      this.showMessageErrorPopUp = true;
      this.messageErrorInputsPopUp = "";
      if(this.dateObligation == null){
        this.messageErrorInputsPopUp += "Debe ingresar una fecha."
      }
      if(!this.inputObligationDescription.isAllOK){
        this.messageErrorInputsPopUp += " Debe ingresar un nombre."
      }
      if(this.isDuplicateDescriptionObligationEdit(this.inputObligationDescription.value)){
        this.messageErrorInputsPopUp += " El nombre ingresado ya existe."
      }
    }
  }

  saveSelectedDetail(){
    if(this.inputDetailDescription.isAllOK && this.inputDetailValue.isAllOK 
      && !this.isDuplicateDescriptionDetail(this.inputDetailDescription.value)){
      if(this.obligationSelected.prices == null){
        this.obligationSelected.prices = new Array<Price>();
      }
      this.detailSelected.description = this.inputDetailDescription.value;
      Logger.debug("Guardando detalle valor " + this.inputDetailValue.value);
      let value: number = Number(this.inputDetailValue.value.replace(",", "."));
      this.detailSelected.value = value;
      Logger.debug("Valor actual del input detalle valor " + this.detailSelected.value);
      this.priceSelected = new Price();
      this.priceSelected.description = this.inputDetailDescription.value;
      this.priceSelected.details = new Array<Detail>();
      this.priceSelected.details.push(this.detailSelected);
      this.priceSelected.validityStartDate = new Date();
      this.priceSelected.value = Number(this.inputDetailValue.value);
      this.obligationSelected.prices.push(this.priceSelected);
      this.refreshListBox();
      this.hideDisplayPopUp();
    }else{
      this.showMessageErrorPopUp = true;
      this.messageErrorInputsPopUp = "";
      if(!this.inputDetailDescription.isAllOK){
        this.messageErrorInputsPopUp += "Debe ingresar un nombre."
      }
      if(!this.inputDetailValue.isAllOK){
        this.messageErrorInputsPopUp += " Debe ingresar un valor."
      }
      if(this.isDuplicateDescriptionDetail(this.inputDetailDescription.value)){
        this.messageErrorInputsPopUp += " El nombre ingresado ya existe."
      }
    }
  }

  saveSelectedDetailEdit(){
    if(this.inputDetailDescription.isAllOK && this.inputDetailValue.isAllOK 
      && !this.isDuplicateDescriptionDetailEdit(this.inputDetailDescription.value)){
      this.detailSelected.description = this.inputDetailDescription.value;
      let value: number = Number(this.inputDetailValue.value.replace(",", "."));
      this.detailSelected.value = value;
      this.refreshListBox();
      this.hideDisplayPopUp();
    }else{
      this.showMessageErrorPopUp = true;
      this.messageErrorInputsPopUp = "";
      if(!this.inputDetailDescription.isAllOK){
        this.messageErrorInputsPopUp += "Debe ingresar un nombre."
      }
      if(!this.inputDetailValue.isAllOK){
        this.messageErrorInputsPopUp += " Debe ingresar un valor."
      }
      if(this.isDuplicateDescriptionDetailEdit(this.inputDetailDescription.value)){
        this.messageErrorInputsPopUp += " El nombre ingresado ya existe."
      }
    }
  }

  saveSelectedLittleDetail(){
    if(this.inputLittleDetailDescription.isAllOK && this.inputLittleDetailValue.isAllOK 
      && !this.isDuplicateDescriptionLittleDetail(this.inputLittleDetailDescription.value)){
      if(this.detailSelected.littleDetails == null){
        this.detailSelected.littleDetails = new Array<LittleDetail>();
      }
      this.littleDetailSelected.description = this.inputLittleDetailDescription.value;
      let value: number = Number(this.inputLittleDetailValue.value.replace(",", "."));
      this.littleDetailSelected.value = value;
      this.detailSelected.littleDetails.push(this.littleDetailSelected);
      let totalPrice: number = 0;
      for(let littleDetail of this.detailSelected.littleDetails){
        totalPrice += littleDetail.value;
      }
      this.detailSelected.value = totalPrice;
      this.refreshListBox();
      this.hideDisplayPopUp();
    }else{
      this.showMessageErrorPopUp = true;
      this.messageErrorInputsPopUp = "";
      if(!this.inputLittleDetailDescription.isAllOK){
        this.messageErrorInputsPopUp += "Debe ingresar un nombre."
      }
      if(!this.inputLittleDetailValue.isAllOK){
        this.messageErrorInputsPopUp += " Debe ingresar un valor."
      }
      if(this.isDuplicateDescriptionLittleDetail(this.inputLittleDetailDescription.value)){
        this.messageErrorInputsPopUp += " El nombre ingresado ya existe."
      }
    }
  }

  saveSelectedLittleDetailEdit(){
    if(this.inputLittleDetailDescription.isAllOK && this.inputLittleDetailValue.isAllOK 
      && !this.isDuplicateDescriptionLittleDetailEdit(this.inputLittleDetailDescription.value)){
      this.littleDetailSelected.description = this.inputLittleDetailDescription.value;
      let value: number = Number(this.inputLittleDetailValue.value.replace(",", "."));
      this.littleDetailSelected.value = value;
      let totalPrice: number = 0;
      for(let littleDetail of this.detailSelected.littleDetails){
        totalPrice += littleDetail.value;
      }
      this.detailSelected.value = totalPrice;
      this.refreshListBox();
      this.hideDisplayPopUp();
    }else{
      this.showMessageErrorPopUp = true;
      this.messageErrorInputsPopUp = "";
      if(!this.inputLittleDetailDescription.isAllOK){
        this.messageErrorInputsPopUp += "Debe ingresar un nombre."
      }
      if(!this.inputLittleDetailValue.isAllOK){
        this.messageErrorInputsPopUp += " Debe ingresar un valor."
      }
      if(this.isDuplicateDescriptionLittleDetailEdit(this.inputLittleDetailDescription.value)){
        this.messageErrorInputsPopUp += " El nombre ingresado ya existe."
      }
    }
  }

  isDuplicateDescriptionObligation(description:string){
   if(this.paymentPlan.obligations != null){
      for(let obligation of this.paymentPlan.obligations){
        if(obligation.description === description){
          return true;
        }
      }
    }   
    return false;
  }

  isDuplicateDescriptionObligationEdit(description:string){
    let rNumber: number = 0;
    for(let obligation of this.paymentPlan.obligations){
      if(obligation.description === description){
        rNumber++;
        if(rNumber > 1){
          return true;
        }
      }
    }
    return false;
  }

  isDuplicateDescriptionDetail(description:string){
    if(this.obligationSelected.prices != null){
      for(let price of this.obligationSelected.prices){
        if(price.details != null){
          for(let detail of price.details){
            if(detail.description === description){
              return true;
            }
          }
        }
      }
    }
    return false;
  }

  isDuplicateDescriptionDetailEdit(description:string){
    let rNumber: number = 0;
    for(let price of this.obligationSelected.prices){
      for(let detail of price.details){
        if(detail.description === description){
          Logger.debug("descripciones iguales: " + detail.description + " : " + description);
          rNumber++;
          Logger.debug("Number: " + rNumber);
          if(rNumber > 1){
            Logger.debug("true");
            return true;
          }
        }
      }
    }
    return false;
  }

  isDuplicateDescriptionLittleDetail(description:string){
    if(this.detailSelected.littleDetails != null){
      for(let littleDetail of this.detailSelected.littleDetails){
        if(littleDetail.description === description){
          return true;
        }
      }
    }
    return false;
  }

  isDuplicateDescriptionLittleDetailEdit(description:string){
    let rNumber: number = 0;
    for(let littleDetail of this.detailSelected.littleDetails){
      if(littleDetail.description === description){
        rNumber++;
        if(rNumber > 1){
          return true;
        }
      }
    }
    return false;
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
 
  newDetail(){
    this.detailSelected = new Detail();
    this.inputDetailDescription.value = "";
    this.inputDetailValue.value = "";
    this.priceContentActive = false;
    this.detailContentActive = true;
    this.obligationContentActive = false;
    this.littleDetailContentActive = false;
  }

  newLittleDetail(){
    this.littleDetailSelected = new LittleDetail();
    this.inputLittleDetailDescription.value = "";
    this.inputLittleDetailValue.value = "";
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.obligationContentActive = false;
    this.littleDetailContentActive = true;
  }

  loadEditObligation(){
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.obligationContentActive = true;
    this.littleDetailContentActive = false;
    this.displayPopUp = "block";
    this.inputObligationDescription.value = this.obligationSelected.description;
    this.dateObligation = this.obligationSelected.dueDate;
    this.inEditPopUp = true;
    this.showMessageErrorPopUp = false;
  }

  loadDeleteObligation(){
    this.paymentPlan.obligations.splice(this.paymentPlan.obligations.indexOf(this.obligationSelected));
    this.refreshListBox();
  }

  loadEditDetail(){
    this.detailContentActive = true;
    this.obligationContentActive = false;
    this.littleDetailContentActive = false;
    this.displayPopUp = "block";
    this.inputDetailDescription.value = this.detailSelected.description;
    Logger.debug("Cargando detalle valor " + this.detailSelected.value);
    this.inputDetailValue.value = "" + this.detailSelected.value;
    this.inputDetailValue.value = this.inputDetailValue.value.replace(".", ",");
    Logger.debug("Valor actual del input detalle valor " + this.inputDetailValue.value);
    this.inEditPopUp = true;
    this.showMessageErrorPopUp = false;
  }

  loadDeleteDetail(){
    for(let i = 0; i < this.obligationSelected.prices.length; i++){
      for(let j = 0; j < this.obligationSelected.prices[i].details.length; j++){
        if(this.obligationSelected.prices[i].details[j].description === this.detailSelected.description){
          this.obligationSelected.prices[i].details.splice(j);
        }
      }
    }
    this.refreshListBox();
  }


  loadEditLittleDetail(){
    this.detailContentActive = false;
    this.obligationContentActive = false;
    this.littleDetailContentActive = true;
    this.displayPopUp = "block";
    this.inputLittleDetailDescription.value = this.littleDetailSelected.description;
    this.inputLittleDetailValue.value = "" + this.littleDetailSelected.value;
    this.inputLittleDetailValue.value = this.inputLittleDetailValue.value.replace(".", ",");
    this.inEditPopUp = true;
    this.showMessageErrorPopUp = false;
  }

  loadDeleteLittleDetail(){
    this.detailSelected.littleDetails.splice(this.detailSelected.littleDetails.indexOf(this.littleDetailSelected));
    this.refreshListBox();
  }

  setPaymentPlanDescription(inputModel: InputModel){
    this.inputDescription = inputModel;
  }

  setPaymentPlanObligationDescription(inputModel: InputModel){
    this.inputObligationDescription = inputModel;
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