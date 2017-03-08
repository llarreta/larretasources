import { Component } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'providers-file-payment-detail',
  templateUrl: './src/app/modules/+providers/file-payment/file-payment-detail.component.html'
})
export class FilePaymentDetailComponent {
  newItem = {
    EndTime: null,
    StartTime: null
  };

}