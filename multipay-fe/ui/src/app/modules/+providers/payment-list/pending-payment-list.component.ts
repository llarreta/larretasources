import { Component, Injectable, OnInit, OnDestroy, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbPopoverConfig, NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { PaymentListService } from '../services/payment-list.service';
import { StatusService } from '../services/status.service';
import { PaymentTypeService } from '../services/payment-type.service';

import { AdvancedSearchFilter } from '../models/advanced-search.model';
import { SearchRequest } from '../models/search-request.model';
import { Status } from '../models/status.model';

@Component({
  selector: 'providers-pending-payment-list',
  templateUrl: './src/app/modules/+providers/payment-list/pending-payment-list.component.html'
})

export class PendingPaymentListComponent implements OnInit {

  private advancedSearchFilter: AdvancedSearchFilter;
  private requestAdvancedSearchFilter: AdvancedSearchFilter;

  private searchRequest: SearchRequest;

  private states: Array<String>;

  @Input()
  private paymentTypes: Array<String>;

  @Input()
  private customerNames: Array<String>;

  private payments = [];

  constructor(private config: NgbPopoverConfig, private ps: PaymentListService, private statusService: StatusService) {
    // customize default values of popovers used by this component tree
    this.config.placement = 'left';
    this.config.triggers = 'click';
  }

  ngOnInit() {
    this.searchRequest = new SearchRequest();
    this.advancedSearchFilter = new AdvancedSearchFilter();
    this.requestAdvancedSearchFilter = new AdvancedSearchFilter();
    this.searchRequest.setFilter(this.requestAdvancedSearchFilter);
    this.advancedSearchFilter.state = this.statusDefault;
    this.advancedSearchFilter.paymentType = this.paymentDefault;
    this.advancedSearchFilter.customerName = this.customerDefault;

    this.ps.getPendingPayments().subscribe((x) => {
      this.payments = x.responseObject.body;
    });
    this.statusService.getPendingStates().subscribe( 
      (x) => {
        this.states = x.responseObject.body;
      }
    );
  }

  loadPaymentDetails(index){
    this.payments[index].show = false;
    if(!this.payments[index].openPaymentDetails){
      this.ps.getPaymentDetails(this.payments[index].listId).subscribe((x) => {
        this.payments[index].openPaymentDetails = x.responseObject.body;
        this.payments[index].show = true;
      });
    } else {
        this.payments[index].show = true;
    }
  };

  closePaymentDetails(index){
    this.payments[index].show = false;
  }

  statusDefault = "Seleccione el estado"; 
  paymentDefault = "Seleccione el tipo de pago";
  customerDefault = "Seleccione la empresa";

  selectPendingStatus(index: number) {
    if (index == -1) {
      this.advancedSearchFilter.state = this.statusDefault;
    } else {
      this.advancedSearchFilter.state = this.states[index];
    }
  }

  selectPendingPayment(index: number) {
    if (index == -1) {
      this.advancedSearchFilter.paymentType = this.paymentDefault;
    } else {
      this.advancedSearchFilter.paymentType = this.paymentTypes[index];
    }
  }

  selectCustomerName(index: number) {
    if (index == -1) {
      this.advancedSearchFilter.customerName = this.customerDefault;
    } else {
      this.advancedSearchFilter.customerName = this.customerNames[index];
    }
  }

  pendingColumnField(field: string) {
    if (field == this.searchRequest.orderField) {
      this.switchPendingOrderField();
    } 
    this.searchRequest.orderField = field;
    this.search();
  }

  generalPendingSearch() {
    this.requestAdvancedSearchFilter = Object.assign({},this.advancedSearchFilter);
    
    if (this.requestAdvancedSearchFilter.state == this.statusDefault) {
      this.requestAdvancedSearchFilter.state = undefined;
    }
    if (this.requestAdvancedSearchFilter.paymentType == this.paymentDefault) {
      this.requestAdvancedSearchFilter.paymentType = undefined;
    }
    if (this.requestAdvancedSearchFilter.customerName == this.customerDefault) {
      this.requestAdvancedSearchFilter.customerName = undefined;
    }

    for (var attribute in this.requestAdvancedSearchFilter) {
      if (this.requestAdvancedSearchFilter[attribute] == "") {
        this.requestAdvancedSearchFilter[attribute] = undefined;
      }
    }
    this.search();
  }

  search() {
    this.searchRequest.setFilter(this.requestAdvancedSearchFilter);
    this.ps.getPendingPayments(this.searchRequest).subscribe((x) => {
      this.payments = x.responseObject.body;
    });
  }

  switchPendingOrderField() {
    if (this.searchRequest.order == "asc") {
      this.searchRequest.order = "desc";
    } else {
      this.searchRequest.order = "asc";
    }
  }

  newItem = {
    EndTime: null,
    StartTime: null
  };

}