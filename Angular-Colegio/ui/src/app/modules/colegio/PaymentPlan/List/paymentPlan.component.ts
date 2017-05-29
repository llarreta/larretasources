 import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';
import { PaymentPlan } from '../../Models/PaymentPlan.model';
import { I18n } from '../../../../i18n/i18n';
import { Logger } from '../../../../logger/logger';
import {Header} from 'primeng/primeng';
import {Footer} from 'primeng/primeng';
import { InputCommonsComponent } from '../../Commons/Input/input.component';
import { InputModel } from '../../Commons/Input/input.model.component';

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
  
  private language: string;

  constructor() {}

  ngOnInit() {

    this.language = "ES";
    this.selectedPaymentPlan = new PaymentPlan();
    this.paymentPlans = new Array<PaymentPlan>();
    this.cargarPaymentPlanTest();
    this.inListPaymentPlan = true;
    this.inCreatePaymentPlan = false;
    this.inUpdatePaymentPlan = false;
    this.morePaymentPlans = true;
    this.loading = false;

  }

  protected fetchNextChunk(skip: number, limit: number): Promise<PaymentPlan[]> {
      return new Promise((resolve, reject) => {
          new Array<PaymentPlan>();
      });
  }

  
  cargarPaymentPlanTest(){

    let paymentPlan: PaymentPlan = new PaymentPlan();
    paymentPlan.description = "Plan de pago 1";
    paymentPlan.id = 1;

    let paymentPlan2: PaymentPlan = new PaymentPlan();
    paymentPlan2.description = "Plan de pago 2";
    paymentPlan2.id = 2;
    
    let paymentPlan3: PaymentPlan = new PaymentPlan();
    paymentPlan3.description = "Plan de pago 3";
    paymentPlan3.id = 3;

    let paymentPlan4: PaymentPlan = new PaymentPlan();
    paymentPlan4.description = "Plan de pago 4";
    paymentPlan4.id = 4;

    let paymentPlan5: PaymentPlan = new PaymentPlan();
    paymentPlan5.description = "Plan de pago 5";
    paymentPlan5.id = 5;

    this.paymentPlans = [];
    this.paymentPlans.push(paymentPlan);
    this.paymentPlans.push(paymentPlan2);
    this.paymentPlans.push(paymentPlan3);
    this.paymentPlans.push(paymentPlan4);
    this.paymentPlans.push(paymentPlan5);
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
      this.cargarPaymentPlanTest();
  }

  loadPaymentPlan(paymentPlan: PaymentPlan){
    this.selectedPaymentPlan = paymentPlan;
    this.inUpdatePaymentPlan = true;
    this.inListPaymentPlan = false;
    this.inCreatePaymentPlan = false;
  }

}