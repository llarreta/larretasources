 import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';
import { PaymentPlan } from '../Models/PaymentPlan.model';
import { I18n } from '../../../i18n/i18n';
import { Logger } from '../../../logger/logger';
import { InputCommonsComponent } from '../Commons/Input/input.component';
import { InputModel } from '../Commons/Input/input.model.component';

@Component({
  selector: 'school-paymentRecord',
  templateUrl: './src/app/modules/colegio/PaymentRecord/PaymentRecord.component.html'
})
export class PaymentRecordComponent implements OnInit{

  inList: boolean;

  constructor() {}

  ngOnInit() {
    this.inList = true;
  }
  
  goRecord(goRecord: boolean) {
    this.inList = !goRecord;
  }

}