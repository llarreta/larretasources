import { Component, Input, OnInit, OnDestroy, ViewChild, ChangeDetectorRef } from '@angular/core';
import { PaymentRecord } from '../Models/PaymentRecord.model';
import { Detail } from '../Models/Detail.model';
import { Student } from '../Models/Student.model';
import { I18n } from '../../../i18n/i18n';
import { Logger } from '../../../Logger/logger';
import { ErrorMessages } from '../../../ErrorMessages/ErrorMessages';
import { InputCommonsComponent } from '../Commons/Input/input.component';
import { InputModel } from '../Commons/Input/input.model.component';

//Services
import { PaymentRecordService } from '../services/paymentRecord.service';

@Component({
  selector: 'school-paymentRecord',
  templateUrl: './src/app/modules/colegio/PaymentRecord/paymentRecord.component.html'
})
export class PaymentRecordComponent implements OnInit{

  inList: boolean;
  student: Student;
  paymentRecords: Array<PaymentRecord>;
  paymentRecordsAux: Array<PaymentRecord>;
  paymentRecordSelected: PaymentRecord;
  messageErrorService: string;
  messageErrorInputsPopUp: string;
  displayPopUp: string;

  showMessageErrorPopUp: boolean;
  showMessageError: boolean;
  showMessageErrorInput: boolean;
  showMessageErrorService: boolean;

  onloadObligations: boolean;

  loading: boolean;

  constructor(
              private paymentRecordService: PaymentRecordService,
              private changeDetectorRef : ChangeDetectorRef
            ) {}

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

      this.showLoading();
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
  }

  loadPaymentsRecords(){
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
    this.paymentRecordsAux = new Array<PaymentRecord>();
    
    for(let paymentRecordJSON of data.body.result){
      let paymentRecord: PaymentRecord = new PaymentRecord();
      Object.assign(paymentRecord, paymentRecordJSON);
      paymentRecord.paidOff = false;
      this.paymentRecordsAux.push(paymentRecord);
    }
    
    Logger.debug("Cuotas cargadas.. " + JSON.stringify(this.paymentRecordsAux));
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
    for(let paymentRecordJSON of data.body.result){
      let paymentRecord: PaymentRecord = new PaymentRecord();
      paymentRecord.id = paymentRecordJSON.obligation.id;
      paymentRecord.student = paymentRecordJSON.student;
      paymentRecord.paidOff = true;
      paymentRecord.description = paymentRecordJSON.obligation.description;
      paymentRecord.dueDate = new Date(paymentRecordJSON.obligation.dueDate); 
      paymentRecord.details = new Array<Detail>();
      Object.assign(paymentRecord.details, paymentRecordJSON.obligation.details);  
      let paymentUnpaid: PaymentRecord = this.searchUnpaid(paymentRecord.id);
      if(paymentUnpaid != null){
        this.paymentRecordsAux.splice(this.paymentRecordsAux.indexOf(paymentUnpaid), 1);
      }  
      this.paymentRecordsAux.push(paymentRecord);
    }
    Logger.debug("Cuotas cargadas.. " + JSON.stringify(this.paymentRecordsAux));
    this.paymentRecords = this.paymentRecordsAux;
    this.onloadObligations = false;
    this.hideLoading();
  }

  searchUnpaid(id: number){
    for(let paymentRecord of this.paymentRecordsAux){
      if(paymentRecord.id == id){
        return paymentRecord;
      }
    }
    return null;
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
    this.hidePopUp();
    this.showLoading();
    this.paymentRecordService.payObligation(this.getTotalValue(), this.student.id, this.paymentRecordSelected.id).subscribe(
          data => this.payObligationOK(data),
          err => this.loadErrorMessageService(err),
          () => console.log('Vacio')
    );
  }

  payObligationOK(data){
    //this.getPayVoucher();
    this.loadData(null);
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
    this.loading = true;
    this.changeDetectorRef.detectChanges();
  }

  hideLoading(){
    this.loading = false;
    this.changeDetectorRef.detectChanges();
  }

}