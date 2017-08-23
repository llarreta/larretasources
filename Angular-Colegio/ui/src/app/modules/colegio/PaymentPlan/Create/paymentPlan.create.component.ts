//Angular components
import { Component, Input, Output, OnInit, OnDestroy, EventEmitter, ViewChild, ChangeDetectorRef } from '@angular/core';
import { DatePipe } from '@angular/common'

//Models
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { Obligation } from '../../Models/Obligation.model';
import { Detail } from '../../Models/Detail.model';
import { LittleDetail } from '../../Models/LittleDetail.model';

//Commons
import { InputModel } from '../../Commons/Input/input.model.component';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';
import { Logger } from '../../../../Logger/logger';
import { MaskTemplates } from '../../Commons/Input/mask.templates';

//ngPrime
import { SelectItem } from 'primeng/primeng';

//Services
import { PaymentPlanService } from '../../services/paymentPlan.service';

@Component({
  selector: 'school-paymentPlan-create',
  templateUrl: './src/app/modules/colegio/PaymentPlan/Create/paymentPlan.create.component.html'
})
export class PaymentPlanCreateComponent implements OnInit{

  @Output()
  goList = new EventEmitter();
  @Input()
  inEdit: Boolean;
  @Input()
  paymentPlan: PaymentPlan;
  @ViewChild("descriptionComponent")
  descriptionComponent: InputCommonsComponent;

  inputDescription: InputModel;
  inputObligationDescription: InputModel;
  inputDetailDescription: InputModel;
  inputDetailValue: InputModel;
  inputLittleDetailDescription: InputModel;
  inputLittleDetailValue: InputModel;
  dateObligation: Date;

  obligationSelected: Obligation;
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
  errorItems: boolean;
  errorObligation: boolean;

  displayPopUp: string;
  obligationContentActive: Boolean;
  priceContentActive: Boolean;
  detailContentActive: Boolean;
  littleDetailContentActive: Boolean;

  obligations: Array<SelectItem>;
  details: Array<SelectItem>;
  littleDetails: Array<SelectItem>;

  inEditPopUp: boolean = false;

  displayConfirmPopUp: string;

  loading: boolean;

  constructor(private paymentPlanService: PaymentPlanService, private changeDetectorRef: ChangeDetectorRef) {}

  ngOnInit() {
    this.initVariablesSelected();
    this.hideDisplayPopUp();
    this.obligationContentActive = false;
    this.priceContentActive = false;
    this.detailContentActive = false;
    this.littleDetailContentActive = false;
    if(!this.inEdit){
      this.paymentPlan = new PaymentPlan();
    }
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
    if(this.inEdit){
      this.inputDescription.value = this.paymentPlan.description;
      this.refreshListBox();
    }
  }

  refreshListBox(){
    Logger.debug("refreshlistbox");
    this.obligations = new Array<SelectItem>();
    this.details = new Array<SelectItem>();
    this.littleDetails = new Array<SelectItem>();
    if((this.paymentPlan != null) && (this.paymentPlan.obligations != null) 
      && (this.paymentPlan.obligations.length > 0)){
      for(let obligation of this.paymentPlan.obligations){
        this.obligations.push({label:obligation.description, value:obligation});
      }
      if((this.obligationSelected != null) && (this.obligationSelected.details != null) && (this.obligationSelected.details.length > 0)){
        Logger.debug("obligation selected: " + JSON.stringify(this.obligationSelected));
        for(let detail of this.obligationSelected.details){
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
    Logger.debug("obligations: " + JSON.stringify(this.obligations));
  }

  initVariablesSelected(){
    this.obligationSelected = new Obligation();
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
    this.showMessageErrorPopUp = false;
    this.inEditPopUp = false;
  }

  showDisplayPopUpDetail(){
    this.newDetail();
    this.displayPopUp = "block";
    this.inputDetailDescription.value = "";
    this.inputDetailDescription.isAllOK = false;
    this.inputDetailValue.isAllOK = false;
    this.showMessageErrorPopUp = false;
    this.inEditPopUp = false;
    if((this.detailSelected.littleDetails != null) && (this.detailSelected.littleDetails.length > 0)){
      this.inputDetailValue.disabled = true;
      this.inputDetailValue.isAllOK = true;
      let totalPrice: number = 0;
      for(let littleDetail of this.detailSelected.littleDetails){
        totalPrice += littleDetail.value;
      }
      this.inputDetailValue.value = String(totalPrice);
    }else{
      this.inputDetailValue.disabled = false;
      this.inputDetailValue.value = "0,00";
      this.inputDetailValue.isAllOK = true;
    }
  }

  showDisplayPopUpLittleDetail(){
    this.inEditPopUp = false;
    this.displayPopUp = "block";
    this.inputLittleDetailDescription.value = "";
    this.inputLittleDetailDescription.isAllOK = false;
    this.inputLittleDetailValue.value = "";
    this.inputLittleDetailValue.isAllOK = false;
    this.showMessageError = false;
    this.showMessageErrorService = false;
    this.showMessageErrorPopUp = false;
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

  hideDisplayPopUpObligation(){
    this.obligationSelected = null;
    this.detailSelected = null;
    this.littleDetailSelected = null;
    this.hideDisplayPopUp();
  }

  hideDisplayPopUpDetail(){
    this.detailSelected = null;
    this.littleDetailSelected = null;
    this.hideDisplayPopUp();
  }

  hideDisplayPopUp(){
    this.displayPopUp = "none";
    this.refreshListBox();
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
    this.inputDescription.minCharacter = 3;
    this.inputDescription.messageErrorMinCharacter = "El nombre debe tener minimo 3 caracteres.";

    this.inputObligationDescription = new InputModel();
    this.inputObligationDescription.id = "namePaymentPlanObligation";
    this.inputObligationDescription.labelContent = "Nombre";
    this.inputObligationDescription.messageErrorEmpty = "Debe indicar el nombre.";
    this.inputObligationDescription.messageErrorValidation = "El nombre ingresado es invalido."
    this.inputObligationDescription.required = true;
    this.inputObligationDescription.type = "text";
    this.inputObligationDescription.validationActivate = true;
    this.inputObligationDescription.minCharacter = 3;
    this.inputObligationDescription.messageErrorMinCharacter = "El nombre debe tener minimo 3 caracteres.";

    this.inputDetailValue = new InputModel();
    this.inputDetailValue.id=  "detailValue";
    this.inputDetailValue.labelContent= "Valor";
    this.inputDetailValue.messageErrorEmpty= "Debe indicar el valor.";
    this.inputDetailValue.messageErrorValidation= "El valor ingresado es invalido."
    this.inputDetailValue.required= true;
    this.inputDetailValue.type= "price";
    this.inputDetailValue.mask= MaskTemplates.price;
    this.inputDetailValue.maskActivate = true;
    this.inputDetailValue.validationActivate = true;
    if(!this.inEdit){
      this.inputDetailValue.value = "0";
    }

    this.inputDetailDescription = new InputModel();
    this.inputDetailDescription.id=  "namePaymentPlanDetail";
    this.inputDetailDescription.labelContent= "Nombre";
    this.inputDetailDescription.messageErrorEmpty= "Debe indicar el nombre.";
    this.inputDetailDescription.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputDetailDescription.required= true;
    this.inputDetailDescription.type= "text";
    this.inputDetailDescription.validationActivate = true;
    this.inputDetailDescription.minCharacter = 3;
    this.inputDetailDescription.messageErrorMinCharacter = "El nombre debe tener minimo 3 caracteres.";

    this.inputLittleDetailDescription = new InputModel();
    this.inputLittleDetailDescription.id=  "namePaymentPlanLittleDetail";
    this.inputLittleDetailDescription.labelContent= "Nombre";
    this.inputLittleDetailDescription.messageErrorEmpty= "Debe indicar el nombre.";
    this.inputLittleDetailDescription.messageErrorValidation= "El nombre ingresado es invalido."
    this.inputLittleDetailDescription.required= true;
    this.inputLittleDetailDescription.type= "text";
    this.inputLittleDetailDescription.validationActivate = true;
    this.inputLittleDetailDescription.minCharacter = 3;
    this.inputLittleDetailDescription.messageErrorMinCharacter = "El nombre debe tener minimo 3 caracteres.";

    this.inputLittleDetailValue = new InputModel();
    this.inputLittleDetailValue.id=  "priceValueLittleDetail";
    this.inputLittleDetailValue.labelContent= "Valor";
    this.inputLittleDetailValue.messageErrorEmpty= "Debe indicar el valor.";
    this.inputLittleDetailValue.messageErrorValidation= "El valor ingresado es invalido."
    this.inputLittleDetailValue.required= true;
    this.inputLittleDetailValue.type= "price";
    this.inputLittleDetailValue.mask = MaskTemplates.price;
    this.inputLittleDetailValue.maskActivate = true;
    this.inputLittleDetailValue.validationActivate = true;
    if(!this.inEdit){
      this.inputLittleDetailValue.value = "0";
    }
  }

  isAllOK(){
    this.descriptionComponent.checkValue();
    if((this.inputDescription.isAllOK) && (this.paymentPlan.obligations != null) 
      && (this.paymentPlan.obligations.length > 0) && (this.ifObligationsHaveOneDetail())){
        this.showMessageErrorInput = false;
        this.showMessageError = false;
        this.errorItems = false;
        this.errorObligation = false;
        return true;
    }else{
      this.messageErrorInputs = "";
      if(!this.inputDescription.isAllOK){
        this.messageErrorInputs += "Debe ingresar un nombre para el plan de pago."
      }
      if((this.paymentPlan.obligations == null) || 
        (this.paymentPlan.obligations.length <= 0)){
          this.errorObligation = true;
          this.messageErrorInputs += " Debe ingresar al menos una cuota para crear un plan de pago."
      }else{
        this.errorObligation = false;
      }
      if(!this.ifObligationsHaveOneDetail()){
        this.messageErrorInputs += " Debe ingresar al menos un item por cuota."
        this.errorItems = true;
      }else{
        this.errorItems = false;
      }
      this.showMessageError = true;
      this.showMessageErrorInput = true;
      return false;
    }
  }

  ifObligationsHaveOneDetail(){
    Logger.debug("ifObligationHaveOneDetail");
    if(this.paymentPlan.obligations != null){
      Logger.debug("this.paymentPlan.obligations no es null");
      for(let obligation of this.paymentPlan.obligations){
        Logger.debug("recorriendo obligations obligation " + JSON.stringify(obligation));
        if((obligation != null)){
          Logger.debug("obligation no es null");
          if((obligation.details == null) || (obligation.details.length <= 0)){
            return false;
          }
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
      this.errorObligation = false;
      this.hideDisplayPopUpObligation();
    }else{
      this.showMessageErrorPopUp = true;
      this.messageErrorInputsPopUp = "";
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
    this.isAllOK();
  }

  saveSelectedObligationEdit(){
    if((this.inputObligationDescription.isAllOK) && (this.dateObligation != null) &&
        (!this.isDuplicateDescriptionObligationEdit(this.inputObligationDescription.value))){
      this.obligationSelected.description = this.inputObligationDescription.value;
      this.obligationSelected.dueDate = this.dateObligation;
      this.refreshListBox();
      this.hideDisplayPopUpObligation();
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
    this.isAllOK();
  }

  saveSelectedDetail(){
    if(this.inputDetailDescription.isAllOK && this.inputDetailValue.isAllOK 
      && !this.isDuplicateDescriptionDetail(this.inputDetailDescription.value)){
      this.detailSelected.description = this.inputDetailDescription.value;
      Logger.debug("Guardando detalle valor " + this.inputDetailValue.value);
      let valueCast = this.inputDetailValue.value.replace("$", "");
      valueCast = valueCast.replace(".", "");
      valueCast = valueCast.replace(",", ".");
      let value: number = Number(valueCast);
      this.detailSelected.value = value;
      Logger.debug("Valor actual del input detalle valor " + this.detailSelected.value);
      if(this.obligationSelected.details == null){
        this.obligationSelected.details = new Array<Detail>();
      }
      this.obligationSelected.details.push(this.detailSelected);
      if(!this.ifObligationsHaveOneDetail()){
        this.errorItems = true;
      }else{
        this.errorItems = false;
      }
      this.refreshListBox();
      this.hideDisplayPopUpDetail();
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
    this.isAllOK();
  }

  saveSelectedDetailEdit(){
    if(this.inputDetailDescription.isAllOK && this.inputDetailValue.isAllOK 
      && !this.isDuplicateDescriptionDetailEdit(this.inputDetailDescription.value)){
      this.detailSelected.description = this.inputDetailDescription.value;
      let valueCast = this.inputDetailValue.value.replace("$", "");
      valueCast = valueCast.replace(".", "");
      valueCast = valueCast.replace(",", ".");
      let value: number = Number(valueCast);
      this.detailSelected.value = value;
      this.refreshListBox();
      this.hideDisplayPopUpDetail();
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
    this.isAllOK();
  }

  saveSelectedLittleDetail(){
    if(this.inputLittleDetailDescription.isAllOK && this.inputLittleDetailValue.isAllOK 
      && !this.isDuplicateDescriptionLittleDetail(this.inputLittleDetailDescription.value)){
      if(this.detailSelected.littleDetails == null){
        this.detailSelected.littleDetails = new Array<LittleDetail>();
      }
      this.littleDetailSelected.description = this.inputLittleDetailDescription.value;
      let valueCast = this.inputLittleDetailValue.value.replace("$", "");
      valueCast = valueCast.replace(".", "");
      valueCast = valueCast.replace(",", ".");
      let value: number = Number(valueCast);
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
      let valueCast = this.inputLittleDetailValue.value.replace("$", "");
      valueCast = valueCast.replace(".", "");
      valueCast = valueCast.replace(",", ".");
      let value: number = Number(valueCast);
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
    if(this.obligationSelected.details != null){
      for(let detail of this.obligationSelected.details){
        if(detail.description === description){
          return true;
        }
      }
    }
    return false;
  }

  isDuplicateDescriptionDetailEdit(description:string){
    let rNumber: number = 0;
    if((this.obligationSelected != null) && (this.obligationSelected.details != null)){
      for(let detail of this.obligationSelected.details){
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
    this.showLoading();
    if(this.isAllOK()){
      this.loadPaymentPlanData();
      this.showMessageError = false;
      let datosResponse;
      let status;
      if(this.inEdit){
        //Llamar servicio update
        Logger.debug("Creando plan de pago...");
        this.paymentPlanService.updatePaymentPlan(this.paymentPlan)
         .subscribe(
          data => this.updatePaymentPlanOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }else{
        //llamar servicio crear
        Logger.debug("Creando plan de pago...");
        this.paymentPlanService.createPaymentPlan(this.paymentPlan)
         .subscribe(
          data => this.createPaymentPlanOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
        );
      }
      
    }else{
      this.hideLoading();
      this.showMessageError = true;
      this.showMessageErrorInput = true;
    }
  }

  createPaymentPlanOK(data){
    this.hideLoading();
    this.goToList();
  }

  updatePaymentPlanOK(data){
    this.hideLoading();
    this.goToList();
  }

  loadErrorMessageService(error){
    this.hideLoading();
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
    this.inputDetailValue.value = "0,00";
    this.priceContentActive = false;
    this.detailContentActive = true;
    this.obligationContentActive = false;
    this.littleDetailContentActive = false;
  }

  newLittleDetail(){
    this.littleDetailSelected = new LittleDetail();
    this.inputLittleDetailDescription.value = "";
    this.inputLittleDetailValue.value = "0,00";
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
    this.dateObligation = new Date(this.obligationSelected.dueDate);
    this.inEditPopUp = true;
    this.showMessageErrorPopUp = false;
  }

  loadDeleteObligation(){
    this.paymentPlan.obligations.splice(this.paymentPlan.obligations.indexOf(this.obligationSelected), 1);
    this.obligationSelected = null;
    this.refreshListBox();
  }

  loadEditDetail(){
    this.detailContentActive = true;
    this.obligationContentActive = false;
    this.littleDetailContentActive = false;
    this.displayPopUp = "block";
    this.inputDetailDescription.value = this.detailSelected.description;
    this.inputDetailDescription.isAllOK = true;
    Logger.debug("Cargando detalle valor " + this.detailSelected.value);
    this.inputDetailValue.value = "" + this.detailSelected.value;
    this.inputDetailValue.value = this.inputDetailValue.value.replace(".", ",");
    Logger.debug("Valor actual del input detalle valor " + this.inputDetailValue.value);
    this.inEditPopUp = true;
    this.showMessageErrorPopUp = false;
    if((this.detailSelected.littleDetails != null) && (this.detailSelected.littleDetails.length > 0)){
      this.inputDetailValue.disabled = true;
      this.inputDetailValue.isAllOK = true;
      let totalPrice: number = 0;
      for(let littleDetail of this.detailSelected.littleDetails){
        totalPrice += littleDetail.value;
      }
      this.inputDetailValue.value = String(totalPrice).replace(".", ",");;
    }else{
      this.inputDetailValue.disabled = false;
      this.inputDetailValue.isAllOK = true;
    }
  }

  loadDeleteDetail(){
    this.obligationSelected.details.splice(this.obligationSelected.details.indexOf(this.detailSelected), 1);
    this.detailSelected = null;
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
    Logger.debug("Eliminando littleDetail: " + JSON.stringify(this.littleDetailSelected));
    Logger.debug("Index: " + this.detailSelected.littleDetails.indexOf(this.littleDetailSelected));
    Logger.debug("Little Details: " + JSON.stringify(this.detailSelected.littleDetails));
    this.detailSelected.littleDetails.splice(this.detailSelected.littleDetails.indexOf(this.littleDetailSelected), 1);
    Logger.debug("Little Details que quedaron: " + JSON.stringify(this.detailSelected.littleDetails));
    this.littleDetailSelected = null;
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

  showLoading(){
    this.loading = true;
    this.changeDetectorRef.detectChanges();
  }

  hideLoading(){
    this.loading = false;
    this.changeDetectorRef.detectChanges();
  }

  confirmDelete(){
    this.displayConfirmPopUp = "block";
  }

  hideDisplayConfirmPopUp(){
    this.displayConfirmPopUp = "none";
  }

  deleteSelectedPaymentPlan(){
    this.showLoading();
    this.paymentPlanService.deletePaymentPlan(this.paymentPlan)
       .subscribe(
        data => this.deletePaymentPlanOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion paymentPlanService...')
    );
  }

  deletePaymentPlanOK(data){
    Logger.debug("Plan de pago eliminado...");
    this.goToList();
    this.hideLoading();
  }

}