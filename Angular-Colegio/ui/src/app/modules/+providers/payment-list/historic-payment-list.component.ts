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
  selector: 'providers-historic-payment-list',
  templateUrl: './src/app/modules/+providers/payment-list/historic-payment-list.component.html'
})

export class HistoricPaymentListComponent implements OnInit {

  private advancedSearchHistoricalFilter: AdvancedSearchFilter;
  private requestAdvancedSearchFilter: AdvancedSearchFilter;

  private searchRequest: SearchRequest;

  private historicStates: Array<String>;

  @Input()
  private paymentTypes: Array<String>;

  @Input()
  private customerNames: Array<String>;

  private historicPayments = [];

  constructor(private config: NgbPopoverConfig, private ps: PaymentListService, private statusService: StatusService) {
    // customize default values of popovers used by this component tree
    this.config.placement = 'left';
    this.config.triggers = 'click';
  }

  ngOnInit() {

    this.searchRequest = new SearchRequest();
    this.advancedSearchHistoricalFilter = new AdvancedSearchFilter();
    this.requestAdvancedSearchFilter = new AdvancedSearchFilter();
    this.searchRequest.setFilter(this.requestAdvancedSearchFilter);

    this.setDefaultDate();

    this.search();

    this.statusService.getHistoricalStates().subscribe(
      (x) => {
        this.historicStates = x.responseObject.body;
      }
    );

    this.advancedSearchHistoricalFilter.state = this.statusDefault;
    this.advancedSearchHistoricalFilter.paymentType = this.paymentDefault;
    this.advancedSearchHistoricalFilter.customerName = this.customerDefault;
  }

  loadPaymentDetails(index) {
    this.historicPayments[index].show = false;
    if (!this.historicPayments[index].openPaymentDetails) {
      this.ps.getPaymentDetails(this.historicPayments[index].listId).subscribe((x) => {
        this.historicPayments[index].openPaymentDetails = x.responseObject.body;
        this.historicPayments[index].show = true;
      });
    } else {
      this.historicPayments[index].show = true;
    }
  };

  closePaymentDetails(index) {
    this.historicPayments[index].show = false;
  }

  statusDefault = "Seleccione el estado";
  paymentDefault = "Seleccione el tipo de pago";
  customerDefault = "Seleccione la empresa";

  selectHistoricalStatus(index: number) {
    if (index == -1) {
      this.advancedSearchHistoricalFilter.state = this.statusDefault;
    } else {
      this.advancedSearchHistoricalFilter.state = this.historicStates[index];
    }
  }

  selectHistoricalPayment(index: number) {
    if (index == -1) {
      this.advancedSearchHistoricalFilter.paymentType = this.paymentDefault;
    } else {
      this.advancedSearchHistoricalFilter.paymentType = this.paymentTypes[index];
    }
  }

  selectCustomerName(index: number) {
    if (index == -1) {
      this.advancedSearchHistoricalFilter.customerName = this.customerDefault;
    } else {
      this.advancedSearchHistoricalFilter.customerName = this.customerNames[index];
    }
  }

  historicalColumnField(field: string) {
    if (field == this.searchRequest.orderField) {
      this.switchHistoricalOrderField();
    }
    this.searchRequest.orderField = field;
    this.search();
  }

  generalHistoricalSearch() {
    this.requestAdvancedSearchFilter = Object.assign({}, this.advancedSearchHistoricalFilter);

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
    this.ps.getHistoricalPayments(this.searchRequest).subscribe((x) => {
      this.historicPayments = x.responseObject.body;
    });
  }

  setDefaultDate() {
    this.advancedSearchHistoricalFilter.dateFrom = new Object();
    var date = new Date();
    date.setDate(date.getDate() - 7);
    this.advancedSearchHistoricalFilter.dateFrom.day = date.getDate();
    this.advancedSearchHistoricalFilter.dateFrom.month = date.getMonth() + 1;
    this.advancedSearchHistoricalFilter.dateFrom.year = date.getFullYear();
    this.requestAdvancedSearchFilter = this.advancedSearchHistoricalFilter;
  }

  switchHistoricalOrderField() {
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