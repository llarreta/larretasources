import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';
import { PaymentRecord } from '../Models/PaymentRecord.model';
import { Student } from '../Models/Student.model';
import { I18n } from '../../../i18n/i18n';
import { Logger } from '../../../logger/logger';
import { ErrorMessages } from '../../../ErrorMessages/ErrorMessages';
import { InputCommonsComponent } from '../Commons/Input/input.component';
import { InputModel } from '../Commons/Input/input.model.component';

//Services
import { PaymentPlanService } from '../services/paymentPlan.service';

@Component({
  selector: 'school-paymentRecord',
  templateUrl: './src/app/modules/colegio/PaymentRecord/PaymentRecord.component.html'
})
export class PaymentRecordComponent implements OnInit{

  inList: boolean;
  student: Student;
  paymentRecords: Array<PaymentRecord>;
  messageErrorService: string;

  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;

  constructor(private paymentPlanService: PaymentPlanService) {}

  ngOnInit() {
    this.inList = true;
  }

  loadData(event) {
    this.loadPaymentsRecords();
  }

  loadPaymentRecordsOK(data){
    this.paymentRecords = new Array<PaymentRecord>();
    for(let paymentRecordJSON of data.body.result){
      let paymentRecord: PaymentRecord = new PaymentRecord();
      Object.assign(paymentRecord, paymentRecordJSON);
      this.paymentRecords.push(paymentRecord);
    }
    Logger.debug("Cuotas cargadas.. " + JSON.stringify(this.paymentRecords));
  }

  loadErrorMessageService(error){
    this.hideLoading();
    Logger.warn("Ocurrio un error..");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }
  
  goRecord(goRecord: boolean) {
    Logger.debug("payment record go record " + goRecord);
    this.inList = !goRecord;
  }

  selectStudent(student: Student){
    Logger.debug("Seleccionando estudiante para paymentRecord");
    this.student = student;
    this.loadPaymentsRecords();
  }

  loadPaymentsRecords(){
    this.showLoading();
    this.paymentPlanService.loadPaymentRecords(this.student.id).subscribe(
          data => this.loadPaymentRecordsOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
    );
  }

  goToList(goList: boolean){
    this.inList = goList;
  }

  showLoading(){
    //this.displayLoading = "block";
  }

  hideLoading(){
    //this.displayLoading = "none";
  }

}