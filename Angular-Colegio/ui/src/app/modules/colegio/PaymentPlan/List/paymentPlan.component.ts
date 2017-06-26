//Angular components
import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';

//Models
import { PaymentPlan } from '../../Models/PaymentPlan.model';

//Commons
import { I18n } from '../../../../i18n/i18n';
import { Logger } from '../../../../logger/logger';
import {Header} from 'primeng/primeng';
import {Footer} from 'primeng/primeng';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { InputModel } from '../../Commons/Input/input.model.component';
import { ErrorMessages } from '../../../../ErrorMessages/ErrorMessages';

//Services
import { PaymentPlanService } from '../../services/paymentPlan.service';

@Component({
  selector: 'school-paymentPlans',
  templateUrl: './src/app/modules/colegio/PaymentPlan/List/PaymentPlan.component.html'
})
export class PaymentPlanComponent implements OnInit{

  paymentPlans: Array<PaymentPlan>;
  selectedPaymentPlan: PaymentPlan;
  inCreatePaymentPlan: boolean;
  inUpdatePaymentPlan: boolean;
  inListPaymentPlan: boolean;
  loading: boolean;
  morePaymentPlans: boolean;
  filterName: string;

  //Errors
  showMessageError: boolean;
  showMessageErrorService: boolean; 
  messageErrorService: string;

  displayLoading: string;
  
  private language: string;

  constructor(private paymentPlanService: PaymentPlanService) {}

  ngOnInit() {

    this.language = "ES";
    this.selectedPaymentPlan = new PaymentPlan();
    this.paymentPlans = new Array<PaymentPlan>();
    this.loadPaymentPlans();
    this.inListPaymentPlan = true;
    this.inCreatePaymentPlan = false;
    this.inUpdatePaymentPlan = false;
    this.morePaymentPlans = true;
    this.loading = false;

  }

  protected fetchNextChunk(skip: number, limit: number): Promise<PaymentPlan[]> {
      return new Promise((resolve, reject) => {
          this.loadPaymentPlans();
      });
  }

  
  loadPaymentPlans(){
    this.showLoading();
    this.paymentPlanService.loadPaymentPlans()
       .subscribe(
        data => this.loadPaymentPlansOK(data),
        err => this.loadErrorMessageService(err),
        () => Logger.debug('Termino ejecucion paymentPlanService...')
    );
  }

  private loadPaymentPlansOK(data){
    Logger.debug("Planes de pago recuperados: " + JSON.stringify(data.body.result));
    Logger.debug("Cantidad de Planes: " + data.body.result.length);
    this.paymentPlans = new Array<PaymentPlan>();
    for(let paymentPlanJSON of data.body.result){
      let paymentPlan: PaymentPlan = new PaymentPlan();
      Object.assign(paymentPlan, paymentPlanJSON);
      this.paymentPlans.push(paymentPlan);
    }
    Logger.debug("Planes de pago recuperados " + JSON.stringify(this.paymentPlans));
    this.hideLoading();
  }

  loadErrorMessageService(error){
    this.hideLoading();
    Logger.warn("Ocurrio un error al crear un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }

  showLoading(){
    this.displayLoading = "block";
  }

  hideLoading(){
    this.displayLoading = "none";
  }

  goListCreate(goList: boolean) {
    this.inUpdatePaymentPlan = false;
    this.inCreatePaymentPlan = !goList;
    this.inListPaymentPlan = goList;
  }

  goListUpdate(goList: boolean){
    this.inCreatePaymentPlan = false;
    this.inUpdatePaymentPlan = !goList;
    this.inListPaymentPlan = goList;
  }

  loadData(event) {
      this.loadPaymentPlans();
  }

  loadPaymentPlan(paymentPlan: PaymentPlan){
    this.selectedPaymentPlan = paymentPlan;
    this.inUpdatePaymentPlan = true;
    this.inListPaymentPlan = false;
    this.inCreatePaymentPlan = false;
  }

}