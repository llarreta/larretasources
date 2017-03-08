import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { NgbProgressbarModule } from '@ng-bootstrap/ng-bootstrap';

import * as Rx from 'rxjs';
import { Observable } from 'rxjs/Rx';

import { FilePaymentService } from '../services/file-payment.service';
import { ProgressBar } from '../models/progress-bar.model';

@Component({
  selector: 'providers-file-payment',
  templateUrl: './src/app/modules/+providers/file-payment/file-payment.component.html'
})
export class FilePaymentComponent implements OnInit, OnDestroy {

  @Input()
  public progressFile: any =  new ProgressBar({ name: "", amount: "", percentage:0, items: 0 });
  private progressBarSubscriptor: Rx.Subscription;

  constructor(public service: FilePaymentService) { }

  ngOnInit() {
    let progressBar = this.service.progressBar();
    this.progressBarSubscriptor = progressBar
    // Going to take next observables till the condition is fullfiled
    .takeWhile(function(x){ return (x.responseObject.body.percentage === 100 || x.empty === true) ? false : true;}) 
    .subscribe(
      (x) => {
        console.log(x);
        this.progressFile = new ProgressBar(x.responseObject.body);
      },
      () => {
        console.log("Completed");
      }
    );
  }

  progressBarUnsubscribe(): void {
    this.progressBarSubscriptor.unsubscribe();
  }

  ngOnDestroy() {
    this.progressBarUnsubscribe();
  }

}