import { Component, Input, OnInit, OnDestroy, ViewChild} from '@angular/core';
import { PaymentRecord } from '../Models/PaymentRecord.model';
import { Student } from '../Models/Student.model';
import { I18n } from '../../../i18n/i18n';
import { Logger } from '../../../logger/logger';
import { ErrorMessages } from '../../../ErrorMessages/ErrorMessages';
import { InputCommonsComponent } from '../Commons/Input/input.component';
import { InputModel } from '../Commons/Input/input.model.component';

//Services
import { PaymentRecordService } from '../services/paymentRecord.service';

@Component({
  selector: 'school-paymentRecord',
  templateUrl: './src/app/modules/colegio/PaymentRecord/PaymentRecord.component.html'
})
export class PaymentRecordComponent implements OnInit{

  inList: boolean;
  student: Student;
  paymentRecords: Array<PaymentRecord>;
  paymentRecordSelected: PaymentRecord;
  messageErrorService: string;
  messageErrorInputsPopUp: string;
  displayPopUp: string;

  showMessageErrorPopUp: boolean;
  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;

  onloadObligations: boolean;

  constructor(private paymentRecordService: PaymentRecordService) {}

  ngOnInit() {
    this.inList = true;
    this.onloadObligations = false;
    this.hidePopUp();
  }

  hidePopUp(){
    this.displayPopUp = "none";
  }

  showPopUp(){
    this.displayPopUp = "block";
  }

  loadData(event) {
    
    if(!this.onloadObligations){
      
      this.onloadObligations = true;
      this.loadPaymentsRecords();

    }

  }

  loadErrorMessageService(error){
    this.hideLoading();
    Logger.warn("Ocurrio un error..");
    this.messageErrorService = ErrorMessages.getMessageError(error.codeError, "ES");
    this.showMessageErrorService = true;
    this.showMessageError = true;
  }

  loadErrorMessageServiceOnLoadObligations(error){
    this.onloadObligations = false;
    this.loadErrorMessageService(error);
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
    this.loadUnpaidObligations();
  }

  loadUnpaidObligations(){
    this.paymentRecordService.loadUnpaidObligations(this.student.id).subscribe(
          data => this.loadUnpaidObligationsOK(data),
          err => this.loadErrorMessageServiceOnLoadObligations(err),
          () => console.log('Vacio')
    );
  }

  loadUnpaidObligationsOK(data){
    this.paymentRecords = new Array<PaymentRecord>();
    debugger
    for(let paymentRecordJSON of data.body.result){
      let paymentRecord: PaymentRecord = new PaymentRecord();
      Object.assign(paymentRecord, paymentRecordJSON);
      paymentRecord.paidOff = false;
      this.paymentRecords.push(paymentRecord);
    }
    Logger.debug("Cuotas cargadas.. " + JSON.stringify(this.paymentRecords));
    this.loadPaidObligations();

  }

  loadPaidObligations(){
    this.paymentRecordService.loadPaidObligations(this.student.id).subscribe(
          data => this.loadPaidObligationsOK(data),
          err => this.loadErrorMessageServiceOnLoadObligations(err),
          () => console.log('Vacio')
    );
  }

  loadPaidObligationsOK(data){
    debugger
    for(let paymentRecordJSON of data.body.result){
      let paymentRecord: PaymentRecord = new PaymentRecord();
      Object.assign(paymentRecord, paymentRecordJSON);
      paymentRecord.paidOff = true;
      this.paymentRecords.push(paymentRecord);
    }
    Logger.debug("Cuotas cargadas.. " + JSON.stringify(this.paymentRecords));
    this.onloadObligations = false;
  }

  loadPaymentRecord(paymentRecord: PaymentRecord){
    this.paymentRecordSelected = paymentRecord;
    this.showPopUp();
  }

  getTotal(paymentRecord: PaymentRecord){

    let total: number = 0;
    
    if(paymentRecord != null && paymentRecord.details != null){

      for(let detail of paymentRecord.details){

        total += detail.value;

      }
      
    }

    return String(total).replace(".", ",");

  }

  getTotalValue(){

    let total: number = 0;
    
    for(let detail of this.paymentRecordSelected.details){

      total += detail.value;

    }

    return total;

  }

  getValueLabel(value: number){
    return String(value).replace(".", ",");
  }

  goToList(goList: boolean){
    this.inList = goList;
  }

  payObligation(){
    this.paymentRecordService.payObligation(this.getTotalValue(), this.student.id, this.paymentRecordSelected.id).subscribe(
          data => this.payObligationOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
    );
  }

  payObligationOK(data){
    this.getPayVoucher();
  }

  getPayVoucher(){
    this.paymentRecordService.getPayVoucher(this.paymentRecordSelected.id).subscribe(
          data => this.getPayVoucherOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
    );
  }

  getPayVoucherOK(data){
    this.printPDF(data.body.fileContent);
  }

  printPDF(pdf){
    if(pdf != null){
      var byteCharacters = atob(pdf);
      var byteNumbers = new Array(byteCharacters.length);
      var printWindow;
      for (var i = 0; i < byteCharacters.length; i++) {
          byteNumbers[i] = byteCharacters.charCodeAt(i);
      }
      var byteArray = new Uint8Array(byteNumbers);
      var blob = new Blob([byteArray], {type: "pdf"});
      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        printWindow = window.navigator.msSaveOrOpenBlob(blob, 
          "Recibo de pago");
        printWindow.print();
      }else{
        printWindow = window.open("data:application/pdf;base64," + pdf, "_blank");
        printWindow.print();      
      }
    }else{
      Logger.error("El pdf vino vacio...");
    }
  }

  showLoading(){
    //this.displayLoading = "block";
  }

  hideLoading(){
    //this.displayLoading = "none";
  }

}