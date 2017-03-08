import { Component, Injectable, OnInit, OnDestroy, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbModule, NgbPopoverConfig } from '@ng-bootstrap/ng-bootstrap';

import { PaymentListService } from '../services/payment-list.service';
import { StatusService } from '../services/status.service';
import { PaymentTypeService } from '../services/payment-type.service';

import { AdvancedSearchFilter } from '../models/advanced-search.model';
import { SearchRequest } from '../models/search-request.model';
import { Status } from '../models/status.model';

@Component({
  selector: 'providers-payment-list',
  templateUrl: './src/app/modules/+providers/payment-list/payment-list.component.html'
})

export class PaymentListComponent implements OnInit {

  paymentTypes: Array<String>;
  customerNames: Array<String>;

  constructor(private config: NgbPopoverConfig, private paymentTypeService: PaymentTypeService, private pagoListaService: PaymentListService) {
    // customize default values of popovers used by this component tree
    this.config.placement = 'left';
    this.config.triggers = 'click';
  }

  ngOnInit() {
    this.paymentTypes = new Array();
    this.paymentTypeService.getPaymentTypes().subscribe((x) => {
      this.paymentTypes = x.responseObject.body;
    })
    this.customerNames = new Array();
    this.pagoListaService.getCustomerNames().subscribe((x) => {
      for (var i = 0; i < x.responseObject.body.length; i++) {
        this.customerNames.push(x.responseObject.body[i].customerName);
      }
    })
  }

  newItem = {
    EndTime: null,
    StartTime: null
  };

}