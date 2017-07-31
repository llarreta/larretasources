//Angular components
import { Component, Input, OnInit, OnDestroy, ViewChild, ChangeDetectorRef } from '@angular/core';

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
  
  private language: string;

  onLoadPayment: boolean;

  constructor(private paymentPlanService: PaymentPlanService,
  private changeDetectorRef : ChangeDetectorRef) {}

  ngOnInit() {

    this.language = "ES";
    this.selectedPaymentPlan = new PaymentPlan();
    this.paymentPlans = new Array<PaymentPlan>();
    this.onLoadPayment = false;
    this.loading = false;
    this.loadPaymentPlans();
    this.inListPaymentPlan = true;
    this.inCreatePaymentPlan = false;
    this.inUpdatePaymentPlan = false;
    this.morePaymentPlans = true;

  }

  loadPaymentPlans(){
    this.showLoading();
    this.onLoadPayment = true;
    this.paymentPlanService.loadPaymentPlans()
       .subscribe(
        data => this.loadPaymentPlansOK(data),
        err => this.loadErrorMessageServicePaymentPlans(err),
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
    this.onLoadPayment = false;
    this.hideLoading();
  }

  loadErrorMessageServicePaymentPlans(error){
    this.onLoadPayment = false;
    this.loadErrorMessageService(error);
  }

  loadErrorMessageService(error){
    this.hideLoading();
    Logger.warn("Ocurrio un error al crear un estudiante...");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }

  hideLoading(){
    this.loading = false;
    this.changeDetectorRef.detectChanges();
  }

  showLoading(){
    this.loading = true;
    this.changeDetectorRef.detectChanges();
  }

  goListCreate(goList: boolean) {
    this.inUpdatePaymentPlan = false;
    this.inCreatePaymentPlan = !goList;
    this.inListPaymentPlan = goList;
    this.changeDetectorRef.detectChanges();
  }

  goListUpdate(goList: boolean){
    this.inCreatePaymentPlan = false;
    this.inUpdatePaymentPlan = !goList;
    this.inListPaymentPlan = goList;
    this.changeDetectorRef.detectChanges();
  }

  loadData(event) {
    
    if(!this.onLoadPayment){
    
      this.loadPaymentPlans();
    
    }

  }

  loadPaymentPlan(paymentPlan: PaymentPlan){
    this.selectedPaymentPlan = paymentPlan;
    this.inUpdatePaymentPlan = true;
    this.inListPaymentPlan = false;
    this.inCreatePaymentPlan = false;
  }

}