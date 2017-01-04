import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { NgbTypeaheadModule, NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { Observable } from 'rxjs/Observable';

import { CompanyService } from '../services/company.service';
import { ProviderService } from '../services/provider.service';

export class KeyValue {
  id: number;
  name: string;

  constructor(id: number, name: string) {
    this.id = id;
    this.name = name;
  }
}

@Component({
  selector: 'providers-manual-payment',
  templateUrl: './src/app/modules/+providers/manual-payment/manual-payment.component.html'
})
export class ManualPaymentComponent {

  constructor(
    private cs: CompanyService,
    private ps: ProviderService) {}

  public endpoint: string = "test";

  newItem = {
    EndTime: null,
    StartTime: null
  };

  public itemsOfautocompleteCompany = Array<KeyValue>();
  autocompleteCompany(event) {
      this.cs.findCompany(event).subscribe((x) => {
          this.itemsOfautocompleteCompany = x.responseObject.body.result.items;
      });
  }

  public itemsOfautocompleteProvider = Array<KeyValue>();
  autocompleteProvider(event) {
      this.ps.findProvider(event).subscribe((x) => {
          this.itemsOfautocompleteProvider = x.responseObject.body.result.items;
      });
  }

  //Providers
  proveedores = [{companyname: "Test", amount: 14}];
 
  getTotal() {
    let total = 0;
    for (var i = 0; i < this.proveedores.length; i++) {
      if (this.proveedores[i].amount) {
        total += this.proveedores[i].amount;
      }
    }
    return total;
  }

}